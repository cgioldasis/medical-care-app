package gr.aueb.cf.medicalcare.dto.treatment;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
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
public class TreatmentUpdateDTO extends BaseDTO {
    @NotNull
    private String treatmentName;
    @NotNull
    private String doctorSsid;
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Set<String> medicineNames;

    public TreatmentUpdateDTO(@NotNull Long id, String treatmentName, String doctorSsid, LocalDate startDate,
                              LocalDate endDate, Set<String> medicineNames) {
        super(id);
        this.treatmentName = treatmentName;
        this.doctorSsid = doctorSsid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicineNames = medicineNames;
    }
}
