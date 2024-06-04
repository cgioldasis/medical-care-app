package gr.aueb.cf.medicalcare.service.User;

import gr.aueb.cf.medicalcare.dto.user.UserRegisterDTO;
import gr.aueb.cf.medicalcare.dto.user.UserUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.UserMapper;
import gr.aueb.cf.medicalcare.model.Role;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.repository.UserRepository;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for User objects
 * Contains methods for user operations
 */

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    // Injecting the UserRepository
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * Get a user by username
     * @param username                  The username of the user
     * @return                          The user
     * @throws UserNotFoundException    If the user is not found
     */
    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        try {
            // Return the user if found, otherwise throw an exception
            return userRepository.findByUsername(username).orElseThrow(() ->
                    new UserNotFoundException("User with username: " + username + " not found"));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    /**
     * Get a user by id
     * @param id                        The id of the user
     * @return                          The user
     * @throws UserNotFoundException    If the user is not found
     */
    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        try {
            // Return the user if found, otherwise throw an exception
            return userRepository.findById(id).orElseThrow(() ->
                    new UserNotFoundException("User with id: " + id + " not found"));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<User> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users;
    }

    /**
     * All the users with a specific role
     * @param role                    The role of the user
     * @return                        The users
     * @throws UserNotFoundException  If the user is not found
     */
    @Override
    public Optional<User> getUserByRole(String role) throws UserNotFoundException {
        Optional<User> users = userRepository.findByRole(Role.valueOf(role));
        if (users.isEmpty()) {
            throw new UserNotFoundException("Users with role: " + role + " not found");
        }
        return users;
    }

    /**
     * Register a new user
     * @param dto                               The user registration data
     * @return                                  The user
     * @throws EntityAlreadyExistsException     If the user already exists
     */
    @Override
    public User registerNewAdminUser(UserRegisterDTO dto) throws EntityAlreadyExistsException {
        User userAdmin = new User();
        try {
            if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new EntityAlreadyExistsException(User.class, dto.getUsername());
            }
            userAdmin = UserMapper.registerUserMapper(dto);
            userAdmin.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(userAdmin);
            log.info("User with username: {} was added at {}", userAdmin.getUsername(), LocalDateTime.now());
        } catch (EntityAlreadyExistsException e) {
            log.error("{} at {}", e.getMessage(), LocalDateTime.now());
            throw e;
        }
        return userAdmin;
    }

    /**
     * Update a user
     * @param dto                           The user update data
     * @return                              The user
     * @throws UserNotFoundException        If the user is not found
     */
    @Override
    public User updateUser(UserUpdateDTO dto) throws UserNotFoundException {
        User user;
        User updatedUser;
        try {
            // If the user is not found, throw an exception
            user = userRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("User with id: "
                    + dto.getId() + " not found"));
            updatedUser = userRepository.save(UserMapper.updateUserMapper(dto, user));
            log.info("User with id: {} was updated at {}", updatedUser.getId(), LocalDateTime.now());
        } catch (UserNotFoundException e) {
            log.error("{} at {}", e.getMessage(), LocalDateTime.now());
            throw e;
        }
        return updatedUser;
    }

    /**
     * Delete a user
     * @param id                            The id of the user
     * @throws UserNotFoundException        If the user is not found
     */
    @Override
    public User deleteUser(Long id) throws UserNotFoundException {
        User user;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
            userRepository.deleteById(id);
            log.info("User with id: {} was deleted successfully at {}", user.getId(), LocalDateTime.now());
            return user;
        } catch (UserNotFoundException e) {
            log.error("{} at {}", e.getMessage(), LocalDateTime.now());
            throw e;
        }
    }
    /**
     * Count the users
     * @return                          The number of users
     */
    @Override
    public Long countUsers() {
        return userRepository.count();
    }
    /**
     * Count the users with a specific role
     * @param role                      The role of the user
     * @return                          The number of users
     */
    @Override
    public Long countUsersByRole(String role) {
        return userRepository.countByRole(Role.valueOf(role));
    }
}
