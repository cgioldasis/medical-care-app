package gr.aueb.cf.medicalcare.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * A class representing a user in the hospital management system.
 * A user can  be a doctor or an admin.
 */

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractEntity {
    // The unique identifier of the entities.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // The role of the user.
    @Enumerated(EnumType.STRING)
    private Role role;
    // The status of the user.
    @Enumerated(EnumType.STRING)
    private Status status;
    // The username of the user.
    private String username;
    // The password of the user.
    private String password;
    // The email of the user.
    private String email;
    // The Doctor associated with this user.
    @OneToOne(mappedBy = "user")
    private Doctor doctor;

    /**
     * Constructor for the User class.
     * @param role      The role of the user.
     * @param status    The status of the user.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param email     The email of the user.
     */
    public User(Role role, Status status, String username, String password, String email) {
        this.role = role;
        this.status = status;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Getting a new user with the doctor role.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @return          The new user with the doctor role.
     */
    public static User getNewUserWithDoctorRole(String username, String password, String email) {
        return new User( Role.DOCTOR, Status.PENDING,
                username, password, email);
    }

    /**
     * Getting a new user with the admin role.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @return          The new user with the admin role.
     */
    public static User getNewUserWithAdminRole(String username, String password, String email) {
        return new User(Role.ADMIN, Status.APPROVED,
                username, password, email);
    }

    /**
     * toString method for the User class.
     * @return      The string representation of the User class.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
