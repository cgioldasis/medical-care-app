package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Represents an admission record in the hospital management system.
 * Extends the AbstractEntity class, inheriting the identifier common property.
 */

@Entity
@Table(name = "admissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admission extends AbstractEntity {

    // The identifier of the admission record.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The status of the patient, e.g. admitted, discharged.
    @NonNull
    private String status;

    //  The admission date.
    private Date admissionDate;

    // The discarded date.
    private Date discharged;

    // The patient associated with this admission.
    @OneToOne(mappedBy = "admission")
    private Patient patient;
}
