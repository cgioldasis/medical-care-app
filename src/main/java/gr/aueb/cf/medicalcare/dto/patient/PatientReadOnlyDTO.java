package gr.aueb.cf.medicalcare.dto.patient;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A DTO class that represents a patient with read-only access.
 */
public class PatientReadOnlyDTO extends BaseDTO {
    // The first name of the patient.
    private String firstname;
    // The last name of the patient.
    private String lastname;
    // The social security number of the patient.
    private String ssid;
    // The phone number of the patient.
    private String phone;
    // The gender of the patient.
    private String gender;
    // The birthdate of the patient.
    private Date birthdate;
    // The description of the disease of the patient.
    private String diseaseDescription;

    public PatientReadOnlyDTO(Long id, String diseaseDescription, String ssid, String firstname, String lastname,
                              String gender, Date birthdate, String phone) {
    }
}
