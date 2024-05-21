package gr.aueb.cf.medicalcare.dto.doctor;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorRegisterDTO {
    // the given username from the register form.
    @Size(min = 3, max = 20)
    private String username;
    // the given password from the register form.
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    private String password;
    // the given password from the register form.
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    private String confirmPassword;
    // the given email from the register form.
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    // the given firstname from the register form.
    @Size(min = 3, max = 20)
    private String firstname;
    // the given lastname from the register form.
    @Size(min = 3, max = 20)
    private String lastname;
    // the given ssid from the register form.
    @Size(min = 9, max = 9)
    private String ssid;
    // the given speciality from the register form.
    @Size(min = 3, max = 20)
    private String specializationName;
    // the given description of the speciality of the doctor.
    private String description;
    // the given gender from the register form.
    private String gender;
    // the given phone from the register form.
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    private String phone;




}
