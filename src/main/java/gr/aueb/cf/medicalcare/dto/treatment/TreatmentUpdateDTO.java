package gr.aueb.cf.medicalcare.dto.treatment;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
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
public class TreatmentUpdateDTO extends BaseDTO {
    // The name of the treatment.
    @NotNull
    private String treatmentName;
    // The start date of the treatment.
    private LocalDate startDate;
    // The end date of the treatment.
    @NotNull
    private LocalDate endDate;
    // The names of the medicines used in the treatment.
    @NotNull
    private Set<String> medicineNames;

    public TreatmentUpdateDTO(@NotNull Long id, String treatmentName, LocalDate startDate,
                              LocalDate endDate, Set<String> medicineNames) {
        super(id);
        this.treatmentName = treatmentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicineNames = medicineNames;
    }
}
