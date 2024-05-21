package gr.aueb.cf.medicalcare.dto.doctor;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DoctorReadOnlyDTO extends BaseDTO {
    // the given username from the register form.
    private String username;
    // the given password from the register form.
    private String password;
    // the given email from the register form.
    private String email;
    // the given firstname from the register form.
    private String firstname;
    // the given lastname from the register form.
    private String lastname;
    // the given ssid from the register form.
    private String ssid;
    // the given speciality from the register form.
    private String specializationName;
    // the given description of the speciality of the doctor.
    private String description;
    // the given gender from the register form.
    private String gender;
    // the given phone from the register form.
    private String phone;

    /**
     * Constructor for DoctorReadOnlyDTO.
     * @param id the id of the doctor.
     * @param email the email of the doctor.
     * @param username the username of the doctor.
     * @param password the password of the doctor.
     * @param firstname the firstname of the doctor.
     * @param lastname the lastname of the doctor.
     * @param ssid the ssid of the doctor.
     * @param specializationName the specialization name of the doctor.
     * @param description the description of the doctor.
     * @param gender the gender of the doctor.
     * @param phone the phone of the doctor.
     */
    public DoctorReadOnlyDTO(@NotNull Long id, String email, String username, String password, String firstname,
                             String lastname, String ssid, String specializationName, String description,
                             String gender, String phone) {
        super(id);
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssid = ssid;
        this.specializationName = specializationName;
        this.description = description;
        this.gender = gender;
        this.phone = phone;
    }
}
