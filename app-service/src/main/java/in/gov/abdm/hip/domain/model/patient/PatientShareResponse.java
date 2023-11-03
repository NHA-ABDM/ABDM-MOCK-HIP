package in.gov.abdm.hip.domain.model.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PatientShareResponse {
    private Acknowledgement acknowledgement;
    private Response response;
}
