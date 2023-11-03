package in.gov.abdm.hip.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static in.gov.abdm.hip.constant.HIPConstant.*;

@UtilityClass
@Slf4j
public class Common {
    public static String loadFileData(String fileName, String authToken, String xAuthToken, String healthLockerUrl, String requestId, String timestamp, String consentManagerId) {
        String content = null;
        try {
            log.info(LOG_PREFIX + INFO_READING_FILE_MSG, requestId, timestamp);
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                contentBuilder.append(line + System.lineSeparator());
            }
            content = contentBuilder.toString();
        } catch (IOException e) {
            log.error(EXCEPTION_OCCURRED_WHILE_READING_FILE_ERROR_MSG, fileName, e.getMessage(), e);
        }
        content = content == null ? null : content.replace(SLASH_N, EMPTY).replace(SLASH_R, EMPTY).trim();

        assert content != null;
        return content.replace(HEALTH_LOCKER_URL, healthLockerUrl)
                .replace(X_TOKEN, xAuthToken)
                .replace(ENV, consentManagerId)
                .replace(API_KEY, authToken);
    }
}
