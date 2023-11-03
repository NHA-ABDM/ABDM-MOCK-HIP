package in.gov.abdm.hip.domain.healthlocker;

import in.gov.abdm.hip.utility.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static in.gov.abdm.hip.constant.HIPConstant.HEALTH_LOCKER_BASE_URL;
import static in.gov.abdm.hip.constant.HIPConstant.HEALTH_LOCKER_FILE;

@Service
@Slf4j
public class HealthLockerService {
    @Value(HEALTH_LOCKER_BASE_URL)
    private String healthLockerUrl;

    public Mono<String> getHealthLocker(String authToken, String xAuthToken, String requestId, String timestamp, String consentManagerId) {
        return Mono.just(Objects.requireNonNull(Common.loadFileData(HEALTH_LOCKER_FILE, authToken, xAuthToken, healthLockerUrl, requestId, timestamp, consentManagerId)));
    }
}
