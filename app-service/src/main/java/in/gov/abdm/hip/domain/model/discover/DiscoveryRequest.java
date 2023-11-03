package in.gov.abdm.hip.domain.model.discover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscoveryRequest {
    private BigInteger id;
    private String transactionId;
    private String patientId;
    private String hipId;
    private Timestamp timestamp;
    private String requestId;

}
