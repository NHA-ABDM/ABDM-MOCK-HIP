package in.gov.abdm.hip.domain.model.consent.notify;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.hip.domain.model.common.PatientId;
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
public class ConsentDetail {
    private String schemaVersion;
    private String consentId;
    private String createdAt;
    private String lastUpdated;
    private PatientId patient;
    private List<CareContext> careContexts;
    private Purpose purpose;
    private ServiceProvider hip;
    private ServiceProvider hiu;
    private ConsentManager consentManager;
    private Requester requester;
    private List<String> hiTypes;
    private ConsentArtefactPermission permission;
}
