package in.gov.abdm.hip.domain.model.discover;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class HealthInformationProviderDiscoverRequest {
    private String transactionId;
    private DiscoverRequestPatient patient;
}
