package in.gov.abdm.hip.domain.model.data.flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {
    private String content;
    private String media;
    private String checksum;
    private String link;
    private String careContextReference;
}
