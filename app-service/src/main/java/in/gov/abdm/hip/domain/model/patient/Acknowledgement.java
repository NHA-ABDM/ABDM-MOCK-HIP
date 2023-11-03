package in.gov.abdm.hip.domain.model.patient;

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
public class Acknowledgement {
    private String status;
    private String abhaAddress;
    private ProfileResponse profile;
    private PaymentResponse payment;
    private HealthInformationResponse healthInformation;

}
