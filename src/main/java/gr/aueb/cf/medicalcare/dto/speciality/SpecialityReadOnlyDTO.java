package gr.aueb.cf.medicalcare.dto.speciality;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for the Speciality entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityReadOnlyDTO {
    // The name of the specialization.
    private String specializationName;
}
