package in.gov.abdm.hip.domain.dataflow;

import in.gov.abdm.error.Response;
import in.gov.abdm.hip.domain.model.data.flow.*;
import in.gov.abdm.hip.utility.Encryptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static in.gov.abdm.hip.constant.HIPConstant.*;

@Service
@Slf4j
public class DataFlowService {

    @Value(FILE_NAME)
    private String fileName;

    public HealthInformationProviderDataFlowResponse prepareDataFlowOnRequest(String requestId, HealthInformationUserDataFlowRequest healthInformationUserDataFlowRequest) {
        return HealthInformationProviderDataFlowResponse.builder()
                .hiRequest(HealthInformationRequestResponse.builder().transactionId(healthInformationUserDataFlowRequest.getTransactionId())
                        .sessionStatus(ACKNOWLEDGED).build())
                .response(new Response(requestId)).build();
    }

    @SneakyThrows
    public DataTransferRequest prepareDataFlowTransferRequest(String requestId, HealthInformationUserDataFlowRequest healthInformationUserDataFlowRequest) {
        EncryptionKeys encryptionKeys = generateEncryptionKeys();
        String sampleJson = Encryptor.loadFileData(fileName);
        String encryptedData = Encryptor.encrypt(sampleJson, encryptionKeys, healthInformationUserDataFlowRequest.getHiRequest().getKeyMaterial());
        return DataTransferRequest.builder()
                .transactionId(healthInformationUserDataFlowRequest.getTransactionId())
                .entries(List.of(Entry.builder().content(encryptedData).media(MEDIA_TYPE_FHIR_JSON)
                        .checksum(DigestUtils.md5Hex(sampleJson).toUpperCase())
                        .careContextReference(CARE_CONTEXT_REFERENCE_NUMBER).build()))
                .keyMaterial(generateKeyMaterial(encryptionKeys)).build();
    }

    private EncryptionKeys generateEncryptionKeys() {
        try {
            return Encryptor.generateKeyMaterial();
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            log.error(EXCEPTION_OCCURRED_WHILE_GENERATE_ENCRYPTION_KEY, e);
            return null;
        }
    }

    private KeyMaterial generateKeyMaterial(EncryptionKeys encryptionKeys) {
        PublicKey publicKey = PublicKey.builder()
                .keyValue(encryptionKeys.getPublicKey())
                .parameters(RANDOM_KEY)
                .expiry(Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .build();
        return KeyMaterial.builder()
                .nonce(encryptionKeys.getNonce())
                .curve(CURVE)
                .dhPublicKey(publicKey)
                .cryptoAlg(ALGORITHM)
                .build();
    }
}
