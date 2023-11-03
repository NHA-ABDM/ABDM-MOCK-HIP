package in.gov.abdm.hip.domain.model.init;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkInitCache {
    private String requestId;
    private String linkReference;
    private String abhaNumber;
    private String abhaAddress;
    private String healthInformationProviderId;
    private String healthInformationUserId;
}
