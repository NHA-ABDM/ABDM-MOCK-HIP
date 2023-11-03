package in.gov.abdm.hip.domain.model.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private String hipId;
    private String context;
    private String hprId;
    private String latitude;
    private String longitude;
}
