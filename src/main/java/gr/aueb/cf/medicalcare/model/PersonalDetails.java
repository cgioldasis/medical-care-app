package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Represents personal details of an individual in the hospital management system.
 * Extends the AbstractEntity class, inheriting common properties like an identifier.
 */
@Entity
@Table(name = "personal-details")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PersonalDetails extends AbstractEntity {

    // The identifier of the personal details.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //  The social security identifier of the individual.
    @Column(nullable = false, unique = true)
    private String ssid;
    //  The first name of the individual.
    @NonNull
    private String firstname;
    //  The last name of the individual.
    @NonNull
    private String lastname;
    //  The gender of the individual.
    @NonNull
    private String gender;
    //  The age of the individual.
    @NonNull
    private Date birthdate;
    //  The phone number of the individual.
    @NonNull
    private String phone;
    // THe patient associated with these personal details.
    @OneToOne(mappedBy = "personalDetails")
    private Patient patient;
    // The staff member associated with these personal details.
    @OneToOne(mappedBy = "personalDetails")
    private Doctor doctor;


    /**
     * Constructor of personal details.
     * @param ssid          The social security identifier of the individual.
     * @param firstname     The first name of the individual.
     * @param lastname      The last name of the individual.
     * @param gender        The gender of the individual.
     * @param birthdate     The birthdate of the individual.
     * @param phone         The phone number of the individual.
     */
    public PersonalDetails(String ssid, @NonNull String firstname, @NonNull String lastname, @NonNull String gender,
                           @NonNull Date birthdate, @NonNull String phone) {
        this.ssid = ssid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
    }


}
