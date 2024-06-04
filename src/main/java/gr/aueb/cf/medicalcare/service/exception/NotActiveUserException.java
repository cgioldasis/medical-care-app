package gr.aueb.cf.medicalcare.service.exception;

import java.io.Serial;

public class NotActiveUserException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3L;

    public NotActiveUserException(String message) {
        super(message);
    }
}
