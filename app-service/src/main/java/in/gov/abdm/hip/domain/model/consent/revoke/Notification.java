package in.gov.abdm.hip.domain.model.consent.revoke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String consentRequestId;
    private String status;
    private List<ConsentArtefactId> consentArtefacts;
}
