package in.gov.abdm.hip.domain.userinit;

import in.gov.abdm.error.Response;
import in.gov.abdm.hip.domain.model.common.CareContext;
import in.gov.abdm.hip.domain.model.confirm.HealthInformationProviderConfirmRequest;
import in.gov.abdm.hip.domain.model.confirm.HealthInformationProviderConfirmResponse;
import in.gov.abdm.hip.domain.model.discover.DiscoverIdentifier;
import in.gov.abdm.hip.domain.model.discover.HealthInformationProviderDiscoverRequest;
import in.gov.abdm.hip.domain.model.discover.HealthInformationProviderOnDiscoverResponse;
import in.gov.abdm.hip.domain.model.discover.OnDiscoverResponsePatient;
import in.gov.abdm.hip.domain.model.init.HealthInformationProviderOnInitResponse;
import in.gov.abdm.hip.domain.model.init.HealthInformationUserInitRequest;
import in.gov.abdm.hip.domain.model.init.Link;
import in.gov.abdm.hip.domain.model.init.Meta;
import in.gov.abdm.hip.domain.redis.RedisAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static in.gov.abdm.hip.constant.HIPConstant.*;

@Service
@Slf4j
public class UserInitService {

    @Autowired
    RedisAuthService redisAuthService;

    public HealthInformationProviderOnDiscoverResponse prepareUserShareOnDiscoverResponse(String requestId, HealthInformationProviderDiscoverRequest healthInformationProviderDiscoverRequest) throws NoSuchAlgorithmException {
        OnDiscoverResponsePatient patient = getResponsePatient(healthInformationProviderDiscoverRequest);
        return HealthInformationProviderOnDiscoverResponse
                .builder()
                .transactionId(healthInformationProviderDiscoverRequest.getTransactionId())
                .response(new Response(requestId))
                .matchedBy(healthInformationProviderDiscoverRequest.getPatient().getUnverifiedIdentifiers().stream().map(DiscoverIdentifier::getType).collect(Collectors.toList()))
                .patient(List.of(patient))
                .build();
    }

    private OnDiscoverResponsePatient getResponsePatient(HealthInformationProviderDiscoverRequest healthInformationProviderDiscoverRequest) throws NoSuchAlgorithmException {
        String currentDateHour = getCurrentDateHour();
        Random random = SecureRandom.getInstanceStrong();
        String display = MOCK_HOSPITAL + UNDERSCORE + random.nextInt(999) + UNDERSCORE + currentDateHour;

        OnDiscoverResponsePatient patient = OnDiscoverResponsePatient.builder().
                referenceNumber(healthInformationProviderDiscoverRequest.getPatient().getId())
                .display(display)
                .hiType(PRESCRIPTION)
                .count(1)
                .careContexts(List.of(CareContext.builder().referenceNumber(UUID.randomUUID().toString().replace("-", "")).display(MOCK_DISPLAY).build()))
                .build();
        redisAuthService.saveToRedis(healthInformationProviderDiscoverRequest.getPatient().getId() + UNDERSCORE + currentDateHour, patient);

        return patient;
    }

    private static String getCurrentDateHour() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);

    }

    public HealthInformationProviderOnInitResponse prepareUserShareOnInitResponse(String requestId, HealthInformationUserInitRequest healthInformationUserInitRequest) {
        String linkReferenceNumber = UUID.randomUUID().toString();
        redisAuthService.saveToRedis(linkReferenceNumber, redisAuthService.getFromRedis(healthInformationUserInitRequest.getAbhaAddress() + UNDERSCORE + getCurrentDateHour()));
        return HealthInformationProviderOnInitResponse
                .builder()
                .transactionId(healthInformationUserInitRequest.getTransactionId())
                .response(new Response(requestId))
                .link(Link.builder().referenceNumber(linkReferenceNumber)
                        .authenticationType(DIRECT)
                        .meta(Meta.builder().communicationMedium(MOBILE)
                                .communicationHint(OTP)
                                .communicationExpiry(getISOTimestamp()).build())
                        .build())
                .build();
    }

    public HealthInformationProviderConfirmResponse prepareUserShareOnConfirmResponse(String requestId, HealthInformationProviderConfirmRequest healthInformationProviderConfirmRequest) {
        OnDiscoverResponsePatient patient = (OnDiscoverResponsePatient) redisAuthService.getFromRedis(healthInformationProviderConfirmRequest.getConfirmation().getLinkRefNumber());
        patient = patient == null ? new OnDiscoverResponsePatient() : patient;
        return HealthInformationProviderConfirmResponse
                .builder()
                .response(new Response(requestId))
                .patient(List.of(patient))
                .build();
    }

    private String getISOTimestamp() {
        SimpleDateFormat targetFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        targetFormat.setTimeZone(TimeZone.getTimeZone(IST_TIMEZONE_ID));
        return targetFormat.format(Timestamp.from(Instant.now().plus(15, ChronoUnit.MINUTES)));
    }
}
