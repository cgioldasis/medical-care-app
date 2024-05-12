package gr.aueb.cf.medicalcare.dto.user;

import gr.aueb.cf.medicalcare.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User data transfer object that is going to be read only.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO extends BaseDTO {
    // the given username from the register form.
    private String username;
    // the given password from the register form.
    private String password;
    // the given email from the register form.
    private String email;

    /**
     * Constructor for the UserReadOnlyDTO.
     * @param id        The id of the user.
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param email     The email of the user.
     */
    public UserReadOnlyDTO(@NotNull Long id, String username, String password, String email) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
