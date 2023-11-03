package in.gov.abdm.hip.domain.model.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String line;
    private String district;
    private String state;
    private String pinCode;
}
