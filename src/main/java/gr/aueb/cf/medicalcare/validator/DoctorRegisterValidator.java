package gr.aueb.cf.medicalcare.validator;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DoctorRegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DoctorRegisterDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorRegisterDTO registerDoctorDTO = (DoctorRegisterDTO) target;

        if (!registerDoctorDTO.getPassword().equals(registerDoctorDTO.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
        }
    }
}
