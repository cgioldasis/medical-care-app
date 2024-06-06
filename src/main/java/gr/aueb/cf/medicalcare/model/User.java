package gr.aueb.cf.medicalcare.model;

import gr.aueb.cf.medicalcare.security.CustomUserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * A class representing a user in the hospital management system.
 * A user can  be a doctor or an admin.
 */

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")

public class User extends AbstractEntity implements CustomUserDetails {
    // The unique identifier of the entities.    status: any;
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
    @Size(min = 3, max = 20)
    @NonNull
    private String username;
    // The password of the user.
    @Column(length = 255)
    @NonNull
    private String password;
    // The email of the user.
    @NonNull
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
        return new User(Role.ADMIN, Status.PENDING,
                username, password, email);
    }

    /**
     * toString method for the User class.
     * @return      The string representation of the User class.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsActive() == null || this.getIsActive();
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
