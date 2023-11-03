package in.gov.abdm.hip.domain.model.confirm;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.hip.domain.model.common.HIPDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link {
    private BigInteger id;
    private String hipId;
    private String healthIdNumber;
    private String linkReference;
    private Map<String, Object> patient;
    private Timestamp dateCreated;
    private String initiatedBy;
    private Timestamp dateModified;
    private HIPDetail hipDetails;
    private String patientId;
    private String hiType;
    private Integer count;
}
