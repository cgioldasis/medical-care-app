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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    //  The personal details of the staff member.
    @OneToOne
    @JoinColumn(name = "personal_details_id", referencedColumnName = "id")
    private PersonalDetails personalDetails;

    //  The specialization of the staff member.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;

    //  The patients assigned to the staff member.
    @OneToMany(mappedBy = "doctor")
    @Getter(AccessLevel.PROTECTED)
    private Set<Patient> patients = new HashSet<>();

    //  The treatments assigned to the staff member.
    @ManyToMany
    @JoinTable(
            name = "doctor_treatments",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    private Set<Treatment> treatments = new HashSet<>();
}
