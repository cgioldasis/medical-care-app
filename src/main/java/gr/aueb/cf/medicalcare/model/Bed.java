package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a bed in the hospital management system.
 * Extends the AbstractEntity class, inheriting common properties like an identifier.
 */

@Entity
@Table(name = "beds", indexes = {
        @Index(name = "bed_number_index", columnList = "bedNumber", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bed extends AbstractEntity {

    // The identifier of the bed.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The bed itself.
    @Column(unique = true, nullable = false)
    private String bedNumber;

    // The room where the bed located.
    @NonNull
    private String room;

    // The sector of the hospital, where bed located.
    @Enumerated(EnumType.STRING)
    private Sector sector;

    // The floor on the hospital, where the bed located.
    @NonNull
    private String floor;

    //  Indicates whether the bed is currently occupied.
    private boolean occupied;

    // The patient assigned to the bed.
    @OneToOne(mappedBy = "bed")
    private Patient patient;


}
