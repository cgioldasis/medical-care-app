package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a treatment in the hospital management system.
 * Extends the AbstractEntity class, inheriting common properties like an identifier.
 */
@Entity
@Table(name = "treatments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Treatment extends AbstractEntity {

    // The identifier of the treatment.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  The name of the treatment.
    @NonNull
    private String treatmentName;

    //  The medicines used in the treatment.
    @ManyToMany
    @Getter(AccessLevel.PROTECTED)
    @JoinTable(
            name = "treatment_medicines",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    )
    private Set<Medicine> medicines = new HashSet<>();

    //  The doctor prescribing the treatment.
    @ManyToMany(mappedBy = "treatments")
    @Getter(AccessLevel.PROTECTED)
    private Set<Doctor> doctors = new HashSet<>();

    //  The dose of the medicine for the treatment.
    @NonNull
    private String dose;

    //  The start date of the treatment.
    @NonNull
    private LocalDate startTreatment;

    //  The end date of the treatment.
    @NonNull
    private LocalDate endTreatment;

    //  The duration of the treatment in days.
//    private Long duration = Duration.between(endTreatment, startTreatment).toDays();

    //  The patient receiving the treatment.
    @OneToMany(mappedBy = "treatment")
    @Getter(AccessLevel.PROTECTED)
    private Set<Patient> patients = new HashSet<>();
}
