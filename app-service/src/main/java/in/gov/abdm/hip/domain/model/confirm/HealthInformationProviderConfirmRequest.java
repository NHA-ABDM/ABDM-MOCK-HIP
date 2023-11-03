package in.gov.abdm.hip.domain.model.confirm;

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
public class HealthInformationProviderConfirmRequest {
    private HealthInformationUserConfirmRequest confirmation;
}
