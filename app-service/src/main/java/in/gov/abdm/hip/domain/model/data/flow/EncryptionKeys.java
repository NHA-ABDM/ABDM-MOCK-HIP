package in.gov.abdm.hip.domain.model.data.flow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncryptionKeys {
    String privateKey;
    String publicKey;
    String nonce;
}