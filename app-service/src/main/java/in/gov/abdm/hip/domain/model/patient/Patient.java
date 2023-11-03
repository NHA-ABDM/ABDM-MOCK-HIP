package in.gov.abdm.hip.domain.model.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private String abhaNumber;
    private String abhaAddress;
    private String name;
    private String gender;
    private String dayOfBirth;
    private String monthOfBirth;
    private String yearOfBirth;
    private String phoneNumber;
}
