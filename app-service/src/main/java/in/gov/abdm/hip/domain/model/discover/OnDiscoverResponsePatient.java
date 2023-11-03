package in.gov.abdm.hip.domain.model.discover;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.hip.domain.model.common.CareContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnDiscoverResponsePatient implements Serializable {

    private String referenceNumber;
    private String display;
    private List<CareContext> careContexts;
    private String hiType;
    private Integer count;
}
