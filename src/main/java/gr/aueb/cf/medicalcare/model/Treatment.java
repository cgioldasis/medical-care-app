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
    @JoinTable(
            name = "treatment_medicines",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    )
    private Set<Medicine> medicines = new HashSet<>();

    //  The doctor prescribing the treatment.
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;


    //  The start date of the treatment.
    @NonNull
    private LocalDate startTreatment;

    //  The end date of the treatment.
    @NonNull
    private LocalDate endTreatment;

    //  The duration of the treatment in days.
//    private Long duration = Duration.between(endTreatment, startTreatment).toDays();

    //  The patient receiving the treatment.
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Patient patient;

    public Treatment(@NonNull String treatmentName, @NonNull LocalDate startTreatment, @NonNull LocalDate endTreatment) {
        this.treatmentName = treatmentName;
        this.startTreatment = startTreatment;
        this.endTreatment = endTreatment;
    }


    public void addMedicine(Medicine medicine) {
        this.medicines.add(medicine);
        medicine.getTreatments().add(this);
    }

    public void addDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.getTreatments().add(this);
    }

    public void addPatient(Patient patient) {
        this.patient = patient;
        patient.setTreatment(this);
    }

    public void setStartDate(LocalDate startDate) {
    }
}
