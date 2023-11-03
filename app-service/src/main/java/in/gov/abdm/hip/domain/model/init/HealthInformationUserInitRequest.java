package in.gov.abdm.hip.domain.model.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthInformationUserInitRequest {
    private String transactionId;
    private String abhaAddress;
    private List<HealthInformationUserInitPatient> patient;
    private ErrorResponse error;
}
