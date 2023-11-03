package in.gov.abdm.hip.utility;

import in.gov.abdm.hip.domain.model.data.flow.EncryptionKeys;
import in.gov.abdm.hip.domain.model.data.flow.KeyMaterial;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;

import javax.crypto.KeyAgreement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;

import static in.gov.abdm.hip.constant.HIPConstant.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Slf4j
public class Encryptor {

    public static String encrypt(String dataToEncrypt, EncryptionKeys senderEncryptionKeys,
                          KeyMaterial receiverKeyMaterial) throws Exception {
        byte[] xorOfRandom = xorOfRandom(senderEncryptionKeys.getNonce(), receiverKeyMaterial.getNonce());

        // Generating shared secret
        String sharedKey = doECDH(getBytesForBase64String(senderEncryptionKeys.getPrivateKey()),
                getBytesForBase64String(receiverKeyMaterial.getDhPublicKey().getKeyValue()));

        // Generating iv and HKDF-AES key
        byte[] iv = Arrays.copyOfRange(xorOfRandom, xorOfRandom.length - 12, xorOfRandom.length);
        byte[] aesKey = generateAesKey(xorOfRandom, sharedKey);
        // Perform Encryption
        String encryptedData = "";
        try {
            byte[] stringBytes = dataToEncrypt.getBytes();

            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            AEADParameters parameters =
                    new AEADParameters(new KeyParameter(aesKey), 128, iv, null);

            cipher.init(true, parameters);
            byte[] plainBytes = new byte[cipher.getOutputSize(stringBytes.length)];
            int retLen = cipher.processBytes
                    (stringBytes, 0, stringBytes.length, plainBytes, 0);
            cipher.doFinal(plainBytes, retLen);

            encryptedData = getBase64String(plainBytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return encryptedData;
    }

    public static EncryptionKeys generateKeyMaterial() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        X9ECParameters ecParameters = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec ecSpec = new ECParameterSpec(ecParameters.getCurve(), ecParameters.getG(),
                ecParameters.getN(), ecParameters.getH(), ecParameters.getSeed());

        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        return EncryptionKeys.builder()
                .nonce(generateRandomKey())
                .privateKey(getBase64String(getEncodedPrivateKey(keyPair.getPrivate())))
                .publicKey(getBase64String(getEncodedPublicKey(keyPair.getPublic())))
                .build();
    }

    private static String generateRandomKey() {
        byte[] salt = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return getBase64String(salt);
    }

    private static byte[] xorOfRandom(String senderNonce, String receiverNonce) {
        byte[] randomSender = getBytesForBase64String(senderNonce);
        byte[] randomReceiver = getBytesForBase64String(receiverNonce);

        byte[] combinedRandom = new byte[randomSender.length];
        for (int i = 0; i < randomSender.length; i++) {
            combinedRandom[i] = (byte) (randomSender[i] ^ randomReceiver[i % randomReceiver.length]);
        }
        return combinedRandom;
    }

    private static byte[] getBytesForBase64String(String value) {
        return org.bouncycastle.util.encoders.Base64.decode(value);
    }

    private static String doECDH(byte[] dataPrv, byte[] dataPub) throws Exception {
        KeyAgreement ka = KeyAgreement.getInstance(ALGORITHM, PROVIDER);
        ka.init(loadPrivateKey(dataPrv));
        ka.doPhase(loadPublicKey(dataPub), true);
        byte[] secret = ka.generateSecret();
        return getBase64String(secret);
    }

    private static PrivateKey loadPrivateKey(byte[] data) throws Exception {
        X9ECParameters ecP = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec params = new ECParameterSpec(ecP.getCurve(), ecP.getG(),
                ecP.getN(), ecP.getH(), ecP.getSeed());
        ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(new BigInteger(data), params);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        return kf.generatePrivate(privateKeySpec);
    }

    private static PublicKey loadPublicKey(byte[] data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        X9ECParameters ecP = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec ecNamedCurveParameterSpec = new ECParameterSpec(ecP.getCurve(), ecP.getG(),
                ecP.getN(), ecP.getH(), ecP.getSeed());

        return KeyFactory.getInstance(ALGORITHM, PROVIDER)
                .generatePublic(new ECPublicKeySpec(ecNamedCurveParameterSpec.getCurve().decodePoint(data),
                        ecNamedCurveParameterSpec));
    }

    private static byte[] generateAesKey(byte[] xorOfRandoms, String sharedKey) {
        byte[] salt = Arrays.copyOfRange(xorOfRandoms, 0, 20);
        HKDFBytesGenerator hkdfBytesGenerator = new HKDFBytesGenerator(new SHA256Digest());
        HKDFParameters hkdfParameters = new HKDFParameters(getBytesForBase64String(sharedKey), salt, null);
        hkdfBytesGenerator.init(hkdfParameters);
        byte[] aesKey = new byte[32];
        hkdfBytesGenerator.generateBytes(aesKey, 0, 32);
        return aesKey;
    }

    private static String getBase64String(byte[] value) {
        return new String(org.bouncycastle.util.encoders.Base64.encode(value));
    }

    private static byte[] getEncodedPublicKey(PublicKey key) {
        ECPublicKey ecKey = (ECPublicKey) key;
        return ecKey.getEncoded();
    }

    private static byte[] getEncodedPrivateKey(PrivateKey key) {
        ECPrivateKey ecKey = (ECPrivateKey) key;
        return ecKey.getD().toByteArray();
    }
    @SneakyThrows
    public static String loadFileData(String fileName) {
        String content = null;
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                contentBuilder.append(line + System.lineSeparator());
            }
            content = contentBuilder.toString();
        } catch (Exception e) {
            log.error(EXCEPTION_OCCURRED_WHILE_READING_FILE_ERROR_MSG, fileName, e.getMessage());
            log.error(e.getMessage(), e);
        }
        return content != null ? content.replace(SLASH_N, EMPTY).replace(SLASH_R, EMPTY).trim() : null;

    }
}