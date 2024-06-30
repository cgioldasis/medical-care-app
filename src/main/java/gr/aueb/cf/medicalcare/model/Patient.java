package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a patient in the hospital management system.
 * Extends the AbstractEntity class, inheriting the
 * identifier property.
 */

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient extends AbstractEntity {

    // The identifier of the patient.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The disease of the patient.
    private String diseaseDescription;

    // Personal details of the patient.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personal_details_id", referencedColumnName = "id")
    private PersonalDetails personalDetails;

    //  Staff assigned to the patient.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

//    //  Bed assigned to the patient.
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false)
//    @JoinColumn(name = "bed_id", referencedColumnName = "id")
//    private Bed bed;

//    //  Admission details of the patient.
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "admission_id", referencedColumnName = "id")
//    private Admission admission;

    //  Treatment details for the patient.
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Treatment treatment;

    public void addPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
        personalDetails.setPatient(this);
    }

    public void addDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.getPatients().add(this);
    }

}
