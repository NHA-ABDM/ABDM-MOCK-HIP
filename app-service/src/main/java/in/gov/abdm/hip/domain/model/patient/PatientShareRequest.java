package in.gov.abdm.hip.domain.model.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientShareRequest {
    private String intent;
    private MetaData metaData;
    private Profile profile;
}
