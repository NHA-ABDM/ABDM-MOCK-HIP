package in.gov.abdm.hip.domain.model.data.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static in.gov.abdm.hip.constant.HIPConstant.DATABASE_TIMEZONE;
import static in.gov.abdm.hip.constant.HIPConstant.TIMESTAMP_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicKey{
    @JsonFormat(pattern = TIMESTAMP_FORMAT, timezone = DATABASE_TIMEZONE)
    private Timestamp expiry;
    private String parameters;
    private String keyValue;
}
