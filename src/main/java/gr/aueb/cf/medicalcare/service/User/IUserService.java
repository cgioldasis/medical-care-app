package gr.aueb.cf.medicalcare.service.User;

import gr.aueb.cf.medicalcare.dto.user.UserRegisterDTO;
import gr.aueb.cf.medicalcare.dto.user.UserUpdateDTO;
import gr.aueb.cf.medicalcare.model.User;

import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    /**
     * Get user by username
     * @param username                The username of the user
     * @return                        The user with the given username
     * @throws UserNotFoundException  If the user with the given username does not exist
     */
    User getUserByUsername(String username) throws UserNotFoundException;
    /**
     * Get user by id
     * @param id                      The id of the user
     * @return                        The user with the given id
     * @throws UserNotFoundException  If the user with the given id does not exist
     */
    User getUserById(Long id) throws UserNotFoundException;
    /**
     * Get all users
     * @return                          The users
     * @throws UserNotFoundException    If any user exists.
     */
    List<User> getAllUsers() throws UserNotFoundException;
    /**
     * Get user by role
     * @param role                      The role of the user
     * @return                          The users with the given role
     * @throws UserNotFoundException    If the users with the given role do not exist
     */
    Optional<User> getUserByRole(String role) throws UserNotFoundException;
    /**
     * Register a new user
     * @param dto                           The user registration data
     * @return                              The new user
     * @throws EntityAlreadyExistsException If the user already exists
     */
    User registerNewAdminUser(UserRegisterDTO dto) throws EntityAlreadyExistsException;
    /**
     * Update a user
     * @param dto                           The user update data
     * @return                              The updated user
     * @throws UserNotFoundException        If the user does not exist
     */
    User updateUser(UserUpdateDTO dto) throws UserNotFoundException;
    /**
     * Delete a user
     * @param id                            The id of the user
     * @throws UserNotFoundException        If the user does not exist
     */
    User deleteUser(Long id) throws UserNotFoundException;
    /**
     * Count all users
     * @return                              The number of users
     */
    Long countUsers();
    /**
     * Count all users by role
     * @param role                          The role of the user
     * @return                              The number of users with the given role
     */
    Long countUsersByRole(String role);



}
