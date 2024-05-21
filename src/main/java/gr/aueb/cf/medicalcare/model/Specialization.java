package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * The specialization of a doctor.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "specializations")
@NoArgsConstructor
@Getter
@Setter
public class Specialization extends AbstractEntity  {

    // The identifier of the specialization.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The name of the specialization.
    @Column(unique = true, nullable = false)
    private String specializationName;

    // The description of the specialization.
    private String description;


    // The doctors that have this specialization.
    @OneToMany(mappedBy = "specialization")
    @Getter(AccessLevel.PROTECTED)
    private Set<Doctor> doctors = new HashSet<>();

    // The constructor of the class with the required fields.
    public Specialization(String specializationName, String description, Boolean isActive) {
        this.specializationName = specializationName;
        this.setIsActive(isActive);
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setSpecialization(this);
    }




}
