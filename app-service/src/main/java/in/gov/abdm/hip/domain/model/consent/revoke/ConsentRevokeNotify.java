package in.gov.abdm.hip.domain.model.consent.revoke;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsentRevokeNotify {
    private String requestId;
    private String timestamp;
    private Notification notification;
}
