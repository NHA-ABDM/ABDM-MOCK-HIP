package in.gov.abdm.hip.domain.model.discover;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.hip.domain.model.common.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class HealthInformationUserDiscoverRequest {

    private Service hip;
    @Valid
    private List<DiscoverIdentifier> unverifiedIdentifiers;
}
