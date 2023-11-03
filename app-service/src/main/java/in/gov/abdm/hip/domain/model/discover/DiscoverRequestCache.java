package in.gov.abdm.hip.domain.model.discover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscoverRequestCache {
    private String requestId;
    private String transactionId;
    private String healthInformationProviderId;
    private String healthInformationUserId;
}
