package gr.aueb.cf.medicalcare.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A class representing the doctors in the hospital management system.
 */

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor extends AbstractEntity {
    // The unique identifier of the entities.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    //  The personal details of the staff member.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_details_id", referencedColumnName = "id")
    private PersonalDetails personalDetails;

    //  The specialization of the staff member.
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;

    //  The patients assigned to the staff member.
    @OneToMany(mappedBy = "doctor")
    @Getter(AccessLevel.PROTECTED)
    private Set<Patient> patients = new HashSet<>();

    //  The treatments assigned to the staff member.
    @OneToMany(mappedBy = "doctor")
    @Getter(AccessLevel.PROTECTED)
    private Set<Treatment> treatments = new HashSet<>();

    public void addUser(User user) {
        this.user = user;
        user.setDoctor(this);
    }

    public void addPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
        personalDetails.setDoctor(this);
    }

    public void addSpecialization(Specialization specialization) {
        this.specialization = specialization;
        specialization.addDoctor(this);
    }

    public void addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.setDoctor(this);
    }
}
