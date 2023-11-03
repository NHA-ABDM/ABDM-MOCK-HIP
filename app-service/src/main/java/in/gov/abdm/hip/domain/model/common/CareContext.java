package in.gov.abdm.hip.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareContext{

    private String referenceNumber;
    private String display;
}
