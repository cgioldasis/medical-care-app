package gr.aueb.cf.medicalcare.dto.treatment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TreatmentRegisterDTO {

    // The name of the treatment.
    @NotNull
    private String treatmentName;
    // The SSID of the doctor prescribing the treatment.
    @NotNull
    private String doctorSsid;
    // The start date of the treatment.
    private LocalDate startDate;
    // The end date of the treatment.
    @NotNull
    private LocalDate endDate;
    // The names of the medicines used in the treatment.
    @NotNull
    private Set<String> medicineNames;
}
