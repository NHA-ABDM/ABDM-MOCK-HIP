package in.gov.abdm.hip.domain.patientshare;

import in.gov.abdm.error.Response;
import in.gov.abdm.hip.domain.model.patient.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static in.gov.abdm.hip.constant.HIPConstant.*;

@Service
@Slf4j
public class PatientShareService {
    private static int tokenNumber = 1;

    public static PatientShareResponse preparePatientProfileShareResponse(String requestId, PatientShareRequest patientShareRequest) {
        return PatientShareResponse
                .builder()
                .response(new Response(requestId))
                .acknowledgement(Acknowledgement
                        .builder()
                        .status(SUCCESS)
                        .abhaAddress(patientShareRequest.getProfile().getPatient().getAbhaAddress())
                        .profile(ProfileResponse
                                .builder()
                                .tokenNumber(tokenNumber++)
                                .context(patientShareRequest.getMetaData().getContext())
                                .expiry(expiry)
                                .build())
                        .build())
                .build();
    }

    public static PatientShareResponse preparePatientPaymentShareResponse(String requestId, PatientShareRequest patientShareRequest) {
        return PatientShareResponse
                .builder()
                .response(new Response(requestId))
                .acknowledgement(Acknowledgement
                        .builder()
                        .status(SUCCESS)
                        .abhaAddress(patientShareRequest.getProfile().getPatient().getAbhaAddress())
                        .payment(PaymentResponse.builder().paymentReference(RANDOM_REFERENCE).build())
                        .build())
                .build();
    }

    public static PatientShareResponse preparePatientRecordShareResponse(String requestId, PatientShareRequest patientShareRequest) {
        return PatientShareResponse
                .builder()
                .response(new Response(requestId))
                .acknowledgement(Acknowledgement
                        .builder()
                        .status(SUCCESS)
                        .abhaAddress(patientShareRequest.getProfile().getPatient().getAbhaAddress())
                        .healthInformation(HealthInformationResponse.builder().healthInformationReference(RANDOM_REFERENCE).build())
                        .build())
                .build();
    }
}
