package lk.ijse.eca.appointmentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    @JsonIgnore
    private String nic;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String picture;
}
