package in.gov.abdm.hip.domain.notify;

import in.gov.abdm.error.Response;
import in.gov.abdm.hip.domain.model.consent.notify.Acknowledgement;
import in.gov.abdm.hip.domain.model.consent.notify.ConsentAcknowledgement;
import in.gov.abdm.hip.domain.model.consent.notify.HIPNotification;
import in.gov.abdm.hip.domain.model.consent.notify.HIPNotificationV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static in.gov.abdm.hip.constant.HIPConstant.ok;

@Service
@Slf4j
public class NotifyService {
    public ConsentAcknowledgement prepareOnNotifyResponse(String requestId, HIPNotificationV3 hipNotificationV3) {
        List<Acknowledgement> acknowledgements = new ArrayList<>();
        for (HIPNotification hipNotification : hipNotificationV3.getNotification()) {
            acknowledgements.add(Acknowledgement
                    .builder()
                    .status(ok).consentId(hipNotification.getConsentId())
                    .build());
        }
        return ConsentAcknowledgement
                .builder()
                .response(new Response(requestId))
                .acknowledgement(acknowledgements)
                .build();
    }
}
