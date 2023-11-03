package in.gov.abdm.hip.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient{

    private String referenceNumber;
    private String display;
    private List<CareContext> careContexts;
    private String hiType;
    private Integer count;
}
