package gr.aueb.cf.medicalcare.validator;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DoctorUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DoctorUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorUpdateDTO updateDTO = (DoctorUpdateDTO) target;

        if (!updateDTO.getPassword().equals(updateDTO.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
        }
    }
}
