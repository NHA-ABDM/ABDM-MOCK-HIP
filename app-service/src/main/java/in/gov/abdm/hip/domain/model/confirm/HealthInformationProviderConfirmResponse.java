package in.gov.abdm.hip.domain.model.confirm;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.ErrorResponse;
import in.gov.abdm.error.Response;
import in.gov.abdm.hip.domain.model.discover.OnDiscoverResponsePatient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthInformationProviderConfirmResponse {
    @Valid
    private List<OnDiscoverResponsePatient> patient;
    private ErrorResponse error;
    private Response response;
}
