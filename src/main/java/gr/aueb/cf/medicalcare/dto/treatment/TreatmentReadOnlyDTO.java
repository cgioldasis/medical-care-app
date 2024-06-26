package gr.aueb.cf.medicalcare.dto.treatment;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Data Transfer Object for the Treatment entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TreatmentReadOnlyDTO extends BaseDTO {
    // The name of the treatment.
    private String treatmentName;
    // The SSID of the doctor prescribing the treatment.
    private String doctorSsid;
    // The ID of the patient receiving the treatment.
    private String patientSsid;
    // The start date of the treatment.
    private LocalDate startDate;
    // The end date of the treatment.
    private LocalDate endDate;
    // The names of the medicines used in the treatment.
    private Set<String> medicineNames;
}
