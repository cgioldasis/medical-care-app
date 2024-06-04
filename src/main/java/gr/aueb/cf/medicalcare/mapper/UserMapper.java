package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.user.UserReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.user.UserRegisterDTO;
import gr.aueb.cf.medicalcare.dto.user.UserUpdateDTO;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.util.SecUtil;

/**
 * Mapper class for User objects
 * Contains methods for mapping User objects to DTOs and vice versa
 */
public class UserMapper {

    /**
     * Private constructor to hide the implicit public one
     */
    private UserMapper() {}



    /**
     * Maps a UserRegisterDTO to a User object
     * @param dto   UserRegisterDTO
     * @return      User
     */
    public static User registerUserMapper(UserRegisterDTO dto) {
        User user = User.getNewUserWithAdminRole(dto.getUsername(), dto.getPassword(), dto.getEmail());
        user.setIsActive(true);
        return user;
    }

    /**
     * Maps a UserUpdateDTO to a User object
     * @param dto   UserUpdateDTO
     * @return      User
     */
    public static User updateUserMapper(UserUpdateDTO dto, User user) {
        user.setUsername(dto.getUsername());
        user.setPassword(SecUtil.hashPassword(dto.getPassword()));
        user.setEmail(dto.getEmail());
        return user;
    }

    /**
     * Maps a User object to a UserReadOnlyDTO
     * @param user  User
     * @return      UserReadOnlyDTO
     */
    public static UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(),user.getUsername(), user.getPassword(), user.getEmail());
    }
}
