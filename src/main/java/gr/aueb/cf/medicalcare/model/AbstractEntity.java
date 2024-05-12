package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * An abstract entity class serving as a base for other entities in the hospital management system.
 * This class provides info about the creation and the last update of the entities and also
 * if it is active or not.
 */

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Serializable {

    // The date and time of the creation of the entity.
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // The date and time of the last update of the entity.
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // A flag indicating if the entity is active or not.
    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     *  A string representation of the object.
     * @return  A string representation of the object.
     */
    @Override
    public String toString() {
        return "AbstractEntity{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                '}';
    }
}
