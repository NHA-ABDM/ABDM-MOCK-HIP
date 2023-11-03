package in.gov.abdm.hip.domain.model.oncarecontext;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.ErrorResponse;
import in.gov.abdm.error.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HIPLinkResponse {
    private String abhaAddress;
    private String status;
    private ErrorResponse error;
    private Response response;
}