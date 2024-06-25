package gr.aueb.cf.medicalcare.dto.doctor;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorForAdminListDTO extends BaseDTO {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String ssid;
    private String specializationName;
    private String phone;

    public DoctorForAdminListDTO(@NotNull Long id, String username, String email, String firstname, String lastname, String ssid, String specializationName, String phone) {
        super(id);
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssid = ssid;
        this.specializationName = specializationName;
        this.phone = phone;
    }
}
