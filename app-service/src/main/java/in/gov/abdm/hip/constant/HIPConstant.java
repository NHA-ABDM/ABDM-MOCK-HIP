package in.gov.abdm.hip.constant;

public class HIPConstant {
    private HIPConstant() {

    }

    public static final String LOG_PREFIX = "ABDM_MOCK_HIP: ";
    public static final String HIP_PATIENT_SHARE = "/hip/patient/share";
    public static final String HIP_DISCOVER = "/hip/patient/care-context/discover";
    public static final String HIP_LINK_INIT = "/hip/link/care-context/init";
    public static final String HIP_LINK_CONFIRM = "/hip/link/care-context/confirm";
    public static final String HIP_DATAFLOW_REQUEST = "/hip/health-information/request";
    public static final String HIP_ON_LINK = "/link/on-care-context";
    public static final String ON_GENERATE_LINK_TOKEN = "/hip/token/on-generate-token";
    public static final String CONSENT_HIP_NOTIFY = "/consent/request/hip/notify";

    public static final String HEALTH_LOCKER = "/hip/health-locker";
    public static final String X_HIP_ID = "X-HIP-ID";
    public static final String PROFILE_SHARE_INTENT = "PROFILE_SHARE";
    public static final String PAYMENT_SHARE_INTENT = "PAYMENT_SHARE";
    public static final String RECORD_SHARE_INTENT = "RECORD_SHARE";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATABASE_TIMEZONE = "Asia/Kolkata";
    public static final String UTC_TIMEZONE_ID = "UTC";
    public static final String SUCCESS = "SUCCESS";
    public static final String RANDOM_REFERENCE = "123456";
    public static final String ON_SHARE_URL = "/patient-share/on-share";
    public static final String ON_DISCOVER_URL = "/user-initiated-linking/patient/care-context/on-discover";
    public static final String ON_LINK_INIT_URL = "/user-initiated-linking/link/care-context/on-init";
    public static final String ON_LINK_CONFIRM_URL = "/user-initiated-linking/link/care-context/on-confirm";
    public static final String ON_DATAFLOW_REQUEST_URL = "/data-flow/health-information/hip/on-request";

    public static final String ON_NOTIFY_REQUEST_URL = "/consent/request/hip/on-notify";
    public static final String TRANSFER_DATAFLOW_REQUEST_URL = "/hiu/v0.5/health-information/transfer";
    public static final String MOCK_DISPLAY = "Sugar Test";
    public static final String ACKNOWLEDGED = "ACKNOWLEDGED";
    public static final String MOCK_HOSPITAL = "LTIM_Encounter";
    public static final String UNDERSCORE = "_";
    public static final String DIRECT = "DIRECT";
    public static final String MOBILE = "MOBILE";
    public static final String OTP = "OTP";
    public static final String IST_TIMEZONE_ID = "IST";
    public static final String DATE_FORMAT = "yyyyMMddHH";
    public static final String PRESCRIPTION = "Prescription";
    public static final String REDIS_SERVER = "${spring.redis.server}";
    public static final String REDIS_PORT = "${spring.redis.port}";
    public static final String REDIS_PASSWORD = "${spring.redis.password}";
    public static final String REDIS_DATABASE = "${spring.redis.database}";
    public static final String REDIS_VALIDITY = "${spring.redis.validity}";
    public static final String REDIS_VALIDITY_IN_MINUTES = "${spring.redis.validity-in-minutes}";
    public static final String EXCEPTION_OCCURRED_WHILE_READING_FILE_ERROR_MSG = "Exception occurred while reading file: {} Error Msg : {}";
    public static final String EMPTY = "";
    public static final String COLON = " : ";
    public static final String SLASH_N = "\n";
    public static final String SLASH_R = "\r";
    public static final String MEDIA_TYPE_FHIR_JSON = "application/fhir+json";
    public static final String URL = " URL ";
    public static final String ok = "ok";
    public static final String AUTHORIZATION = "Authorization";
    public static final String HEALTH_LOCKER_FILE = "health-locker.html";
    public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    public static final String HEALTH_LOCKER_URL = "HEALTH_LOCKER_URL";
    public static final String REQ_ID = "REQ_ID";
    public static final String X_TOKEN = "X_TOKEN";
    public static final String TMST = "TMST";
    public static final String ENV = "ENV";
    public static final String API_KEY = "API_KEY";
    public static final String INFO_READING_FILE_MSG = "load file data for request Id {} and timestamp {}";
    public static final String RANDOM_KEY = "Curve25519/32byte random key";
    public static final String EXCEPTION_OCCURRED_WHILE_GENERATE_ENCRYPTION_KEY = "Failed to generate encryption keys";
    public static final String CARE_CONTEXT_REFERENCE_NUMBER = "RVH1008";
    public static final String FILE_NAME = "${spring.fileName}";
    public static final String HIECM_BASE_URL = "${abdm.hiecm.base-url}";
    public static final String CONSENT_MANAGER_ID = "${abdm.hiecm.cm-id}";
    public static final String HEALTH_LOCKER_BASE_URL = "${abdm.hiecm.health-locker.base-url}";
    public static final String ALGORITHM = "ECDH";
    public static final String CURVE = "curve25519";
    public static final String PROVIDER = "BC";
    public static String expiry = "600";

}
