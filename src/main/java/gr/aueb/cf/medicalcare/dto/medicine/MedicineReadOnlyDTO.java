package gr.aueb.cf.medicalcare.dto.medicine;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
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
public class MedicineReadOnlyDTO extends BaseDTO {
    // The name of the medicine.
    private String medicineName;
    // The active substance of the medicine.
    private String activeSubstance;
    // The manufacturer of the medicine.
    private String manufacturer;
    // The date when the medicine was manufactured.
    private Date manufactureDate;
    // The expiration date of the medicine.
    private Date expirationDate;
}
