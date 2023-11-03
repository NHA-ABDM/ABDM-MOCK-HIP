package in.gov.abdm.hip.domain.model.consent.notify;

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
public class HIPNotification {
    private String status;
    private String consentId;
    private ConsentDetail consentDetail;
    private String signature;

}
