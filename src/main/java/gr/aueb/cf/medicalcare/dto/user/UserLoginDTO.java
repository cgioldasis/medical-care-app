package gr.aueb.cf.medicalcare.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Login DTO for the user. It is used to login to the application.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    // The username of the user.
    private String username;
    // The password of the user.
    private String password;
}
