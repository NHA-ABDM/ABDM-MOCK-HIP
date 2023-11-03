package in.gov.abdm.hip.domain.model.data.flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataTransferRequest {
    private int pageNumber;
    private int pageCount;
    private String transactionId;
    private List<Entry> entries;
    private KeyMaterial keyMaterial;
}
