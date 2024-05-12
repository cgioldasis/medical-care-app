package gr.aueb.cf.medicalcare.validator;

import gr.aueb.cf.medicalcare.dto.user.UserRegisterDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDTO registerUserDTO = (UserRegisterDTO) target;

        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
        }

        if (registerUserDTO.getPassword().length() < 8 || registerUserDTO.getPassword().length() > 16
                || !registerUserDTO.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            errors.rejectValue("password", "password.invalid", "Password is invalid");
        }

        if (!registerUserDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.rejectValue("email", "email.invalid", "Invalid email address");
        }

        if (registerUserDTO.getUsername().length() < 3 || registerUserDTO.getUsername().length() > 16) {
            errors.rejectValue("username", "username.size", "Username must be between 3 and 16 characters long");
        }



    }

}
