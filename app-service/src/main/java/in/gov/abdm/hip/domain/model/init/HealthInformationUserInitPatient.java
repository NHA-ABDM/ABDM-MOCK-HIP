package in.gov.abdm.hip.domain.model.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthInformationUserInitPatient {

    private String referenceNumber;
    private List<CareContext> careContexts;
    private String hiType;
    private Integer count;
}
