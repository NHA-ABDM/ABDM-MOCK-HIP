package in.gov.abdm.hip.domain.model.init;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkReference {
    private BigInteger id;
    private HealthInformationProviderOnInitResponse patientLinkReference;
    private String hipId;
    private Timestamp timestamp;
    private String requestId;
}
