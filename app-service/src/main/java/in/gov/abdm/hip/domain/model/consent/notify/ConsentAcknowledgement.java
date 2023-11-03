package in.gov.abdm.hip.domain.model.consent.notify;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.error.ErrorResponse;
import in.gov.abdm.error.Response;
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
public class ConsentAcknowledgement  {
    private List<Acknowledgement> acknowledgement;
    private ErrorResponse error;
    private Response response;
}