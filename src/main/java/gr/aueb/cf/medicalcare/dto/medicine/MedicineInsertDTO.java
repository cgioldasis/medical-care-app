package gr.aueb.cf.medicalcare.dto.medicine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/**
 * A DTO class that represents a medicine with read-only access.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicineInsertDTO {

    // The name of the medicine.
    @NotNull
    private String medicineName;
    // The active substance of the medicine.
    @NotNull
    private String activeSubstance;
    // The manufacturer of the medicine.
    @NotNull
    private String manufacturer;
    // The date when the medicine was manufactured.
    private Date manufactureDate;
    // The expiration date of the medicine.
    @NotNull
    private Date expirationDate;

}
