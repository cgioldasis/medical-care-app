package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a medicine in the hospital management system.
 * Extends the AbstractEntity class, inheriting common properties like an identifier.
 */

@Entity
@Table(name = "medicines", indexes = {
        @Index(name = "medicine_name_index", columnList = "medicineName", unique = true),
        @Index(name = "active_substance_index", columnList = "activeSubstance", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Medicine extends AbstractEntity {

    // The identifier of the medicine.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  The name of the medicine.`
    @Column(nullable = false, unique = true)
    private String medicineName;

    //  The active substance of the medicine.
    @Column(nullable = false, unique = true)
    private String activeSubstance;

    //  The manufacturer of the medicine.
    @NonNull
    private String manufacturer;

    //  The date when the medicine was manufactured.
    private Date manufactureDate;

    //  The expiration date of the medicine.
    @NonNull
    private Date expirationDate;

    //  The treatment associated with this medicine.
    @ManyToMany(mappedBy = "medicines")
    @Getter(AccessLevel.PROTECTED)
    private Set<Treatment> treatments = new HashSet<>();

}
