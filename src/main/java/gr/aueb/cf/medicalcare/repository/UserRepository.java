package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Role;
import gr.aueb.cf.medicalcare.model.Status;
import gr.aueb.cf.medicalcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * The UserRepository interface extends the JpaRepository interface.
 * It provides the methods to find a user by username, find by role, count
 * all users by role, count all active users by role. add, update
 * and delete a user given by default from the JpaRepository interface.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by username.
    Optional<User> findByUsername(String username);
    // Find all users by role.
    Optional<User> findByRole (Role role);
    // Count all users by role.
    Long countByRole(Role role);
    // Count all active users by role.
    Long countByRoleAndStatus(Role role, Status status);
    // Find a user by email.
    Optional<User> findByEmail(String email);
}
