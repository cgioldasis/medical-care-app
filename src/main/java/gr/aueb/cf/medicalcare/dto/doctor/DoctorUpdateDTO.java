package gr.aueb.cf.medicalcare.dto.doctor;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateDTO extends BaseDTO {
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

    /**
     * Constructor for DoctorUpdateDTO.
     * @param id the id of the doctor.
     * @param username the username of the doctor.
     * @param password the password of the doctor.
     * @param confirmPassword the confirmPassword of the doctor.
     * @param email the email of the doctor.
     * @param firstname the firstname of the doctor.
     * @param lastname the lastname of the doctor.
     * @param ssid the ssid of the doctor.
     * @param specializationName the specialization name of the doctor.
     * @param description the description of the doctor.
     * @param gender the gender of the doctor.
     * @param phone the phone of the doctor.
     */
    public DoctorUpdateDTO(@NotNull Long id, String username, String password, String confirmPassword,
                           String email, String firstname, String lastname, String ssid, String specializationName,
                           String description, String gender, String phone) {
        super(id);
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssid = ssid;
        this.specializationName = specializationName;
        this.description = description;
        this.gender = gender;
        this.phone = phone;
    }
}
