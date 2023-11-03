package in.gov.abdm.hip.domain.model.consent.notify;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.hip.enums.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsentArtefactPermission  {
    private AccessMode accessMode;
    private DateRange dateRange;
    private String dataEraseAt;
    private Frequency frequency;
}
