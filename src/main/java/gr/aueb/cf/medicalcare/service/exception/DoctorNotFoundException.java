package gr.aueb.cf.medicalcare.service.exception;

import java.io.Serial;

public class DoctorNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 2L;

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
