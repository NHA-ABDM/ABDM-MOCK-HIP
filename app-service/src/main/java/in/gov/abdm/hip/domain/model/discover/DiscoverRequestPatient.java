package in.gov.abdm.hip.domain.model.discover;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DiscoverRequestPatient {
    private String id;
    private List<DiscoverIdentifier> verifiedIdentifiers;
    private List<DiscoverIdentifier> unverifiedIdentifiers;
    private String name;
    private String gender;
    private Integer yearOfBirth;
}
