package in.gov.abdm.hip.domain.model.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.ErrorResponse;
import in.gov.abdm.error.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthInformationProviderOnInitResponse {

    private String transactionId;
    private Link link;
    private ErrorResponse error;
    private Response response;
}
