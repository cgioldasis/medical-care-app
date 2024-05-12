package gr.aueb.cf.medicalcare.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User DTO for registering a user.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {
    // the given username from the register form.
    @NotNull
    @Size(min = 3, max = 16)
    private String username;
    // the given password from the register form.
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[@#$%^&+=!]).{8,}$\n",
//            message = "Password must contain at least 8 characters, one uppercase letter," +
//                    " one lowercase letter, one number and one special character.")
    private String password;
    // the given password from the register form.
    private String confirmPassword;
    // the given email from the register form.
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address.")
    private String email;
}
