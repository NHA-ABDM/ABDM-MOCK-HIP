package in.gov.abdm.hip.controller;

import in.gov.abdm.exception.ABDMHIPUnavailableException;
import in.gov.abdm.hip.domain.dataflow.DataFlowService;
import in.gov.abdm.hip.domain.healthlocker.HealthLockerService;
import in.gov.abdm.hip.domain.model.confirm.HealthInformationProviderConfirmRequest;
import in.gov.abdm.hip.domain.model.consent.notify.HIPNotificationV3;
import in.gov.abdm.hip.domain.model.data.flow.HealthInformationUserDataFlowRequest;
import in.gov.abdm.hip.domain.model.discover.HealthInformationProviderDiscoverRequest;
import in.gov.abdm.hip.domain.model.init.HealthInformationUserInitRequest;
import in.gov.abdm.hip.domain.model.linktoken.LinkTokenResponse;
import in.gov.abdm.hip.domain.model.oncarecontext.HIPLinkResponse;
import in.gov.abdm.hip.domain.model.patient.PatientShareRequest;
import in.gov.abdm.hip.domain.model.patient.PatientShareResponse;
import in.gov.abdm.hip.domain.notify.NotifyService;
import in.gov.abdm.hip.domain.patientshare.PatientShareService;
import in.gov.abdm.hip.domain.userinit.UserInitService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static in.gov.abdm.constant.ABDMConstant.*;
import static in.gov.abdm.hiecm.constant.HIECMConstant.X_CM_ID;
import static in.gov.abdm.hiecm.constant.HIECMConstant.X_HIP_ID;
import static in.gov.abdm.hip.constant.HIPConstant.CONSENT_MANAGER_ID;
import static in.gov.abdm.hip.constant.HIPConstant.X_AUTH_TOKEN;
import static in.gov.abdm.hip.constant.HIPConstant.*;


@RestController
@RequestMapping(API_VERSION)
@Validated
@Slf4j
public class HIPController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserInitService userInitService;
    @Autowired
    DataFlowService dataFlowService;
    @Autowired
    NotifyService notifyService;

    @Autowired
    HealthLockerService healthLockerService;

    @Value(HIECM_BASE_URL)
    private String hiecmBaseUrl;

    @Value(CONSENT_MANAGER_ID)
    private String consentManagerId;


    private String getISOTimestamp(Timestamp timestamp) {
        SimpleDateFormat targetFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        targetFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE_ID));
        return targetFormat.format(timestamp);
    }

    private Mono<Void> callABDMGateway(String endpoint, Object o, String hiuUrl) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String baseUrl = hiecmBaseUrl + endpoint;
        String url = hiuUrl != null ? hiuUrl : baseUrl;

        return webClient.post()
                .uri(url)
                .header(REQUEST_ID, UUID.randomUUID().toString())
                .header(TIMESTAMP, getISOTimestamp(Timestamp.from(Instant.now())))
                .header(X_CM_ID, consentManagerId).bodyValue(o).retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> Mono.error(new ABDMHIPUnavailableException()));
    }

    @PostMapping(HIP_PATIENT_SHARE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<PatientShareResponse> patientShare(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) PatientShareRequest patientShareRequest) {
        String intent = patientShareRequest.getIntent();
        log.info(LOG_PREFIX + patientShareRequest);
        if (intent.equalsIgnoreCase(PROFILE_SHARE_INTENT)) {
            callABDMGateway(ON_SHARE_URL, PatientShareService.preparePatientProfileShareResponse(requestId.toString(), patientShareRequest), null).subscribe();
        } else if (intent.equalsIgnoreCase(PAYMENT_SHARE_INTENT)) {
            callABDMGateway(ON_SHARE_URL, PatientShareService.preparePatientPaymentShareResponse(requestId.toString(), patientShareRequest), null).subscribe();
        } else if (intent.equalsIgnoreCase(RECORD_SHARE_INTENT)) {
            callABDMGateway(ON_SHARE_URL, PatientShareService.preparePatientRecordShareResponse(requestId.toString(), patientShareRequest), null).subscribe();
        }
        return Mono.empty();
    }

    @PostMapping(HIP_DISCOVER)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<PatientShareResponse> userShareDiscover(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HealthInformationProviderDiscoverRequest healthInformationUserDiscoverRequest) throws NoSuchAlgorithmException {
        log.info(LOG_PREFIX + healthInformationUserDiscoverRequest);
        callABDMGateway(ON_DISCOVER_URL, userInitService.prepareUserShareOnDiscoverResponse(requestId.toString(), healthInformationUserDiscoverRequest), null).subscribe();
        return Mono.empty();
    }

    @PostMapping(HIP_LINK_INIT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<PatientShareResponse> userShareLinkInit(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HealthInformationUserInitRequest healthInformationUserInitRequest) {
        log.info(LOG_PREFIX + healthInformationUserInitRequest);
        callABDMGateway(ON_LINK_INIT_URL, userInitService.prepareUserShareOnInitResponse(requestId.toString(), healthInformationUserInitRequest), null).subscribe();
        return Mono.empty();
    }

    @PostMapping(HIP_LINK_CONFIRM)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<PatientShareResponse> userShareLinkConfirm(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HealthInformationProviderConfirmRequest healthInformationProviderConfirmRequest) {
        log.info(LOG_PREFIX + healthInformationProviderConfirmRequest);
        callABDMGateway(ON_LINK_CONFIRM_URL, userInitService.prepareUserShareOnConfirmResponse(requestId.toString(), healthInformationProviderConfirmRequest), null).subscribe();
        return Mono.empty();
    }

    @PostMapping(HIP_DATAFLOW_REQUEST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<PatientShareResponse> dataFlowRequest(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HealthInformationUserDataFlowRequest healthInformationUserDataFlowRequest) {
        callABDMGateway(ON_DATAFLOW_REQUEST_URL, dataFlowService.prepareDataFlowOnRequest(requestId.toString(), healthInformationUserDataFlowRequest), null)
                .doOnSuccess(s -> callABDMGateway(TRANSFER_DATAFLOW_REQUEST_URL, dataFlowService.prepareDataFlowTransferRequest(requestId.toString(), healthInformationUserDataFlowRequest), healthInformationUserDataFlowRequest.getHiRequest().getDataPushUrl()).subscribe())
                .subscribe();
        return Mono.empty();
    }

    @PostMapping(HIP_ON_LINK)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> onCareContext(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HIPLinkResponse hipLinkResponse) {
        log.info(LOG_PREFIX + PROCESSING + URL + COLON_SEPARATOR + HIP_ON_LINK);
        return Mono.empty();
    }

    @PostMapping(ON_GENERATE_LINK_TOKEN)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> onGenerateLinkToken(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) LinkTokenResponse linkTokenResponse) {
        log.info(LOG_PREFIX + PROCESSING + URL + COLON_SEPARATOR + ON_GENERATE_LINK_TOKEN);
        return Mono.empty();
    }

    @PostMapping(CONSENT_HIP_NOTIFY)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> onConsentNotify(
            @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
            @RequestHeader(TIMESTAMP) String timestamp,
            @RequestHeader(X_HIP_ID) final String healthInformationUserId,
            @Valid @RequestBody(required = false) HIPNotificationV3 hipNotificationV3) {
        log.info(LOG_PREFIX + PROCESSING + URL + COLON_SEPARATOR + CONSENT_HIP_NOTIFY);
        callABDMGateway(ON_NOTIFY_REQUEST_URL, notifyService.prepareOnNotifyResponse(requestId.toString(), hipNotificationV3), null).subscribe();
        return Mono.empty();
    }

    @GetMapping(HEALTH_LOCKER)
    public Mono<String> verifyEmailLink(@RequestHeader(value = AUTHORIZATION, required = false) String authToken,
                                        @RequestHeader(value = X_AUTH_TOKEN, required = false) String xAuthToken,
                                        @RequestHeader(REQUEST_ID) @NonNull final UUID requestId,
                                        @RequestHeader(TIMESTAMP) String timestamp) {
        try {
            return healthLockerService.getHealthLocker(authToken,xAuthToken,requestId.toString(),timestamp,consentManagerId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Mono.empty();
        }
    }
}