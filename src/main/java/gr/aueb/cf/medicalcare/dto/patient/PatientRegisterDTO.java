package gr.aueb.cf.medicalcare.dto.patient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/**
 * Data Transfer Object for the Patient entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegisterDTO {
    // The first name of the patient.
    @Size(min = 3, max = 20)
    private String firstname;
    // The last name of the patient.
    @Size(min = 3, max = 20)
    private String lastname;
    // The social security number of the patient.
    @Size(min = 9, max = 9)
    private String ssid;
    // The phone number of the patient.
    @Size(min = 10, max = 10)
    private String phone;
    // The gender of the patient.
    @NotNull
    private String gender;
    // The birthdate of the patient.
    @NotNull
    private Date birthdate;
    // The description of the disease of the patient.
    @NotNull
    private String diseaseDescription;

}
