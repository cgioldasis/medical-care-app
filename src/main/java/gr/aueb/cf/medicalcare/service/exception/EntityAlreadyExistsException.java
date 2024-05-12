package gr.aueb.cf.medicalcare.service.exception;

import java.io.Serial;

public class EntityAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(Class<?> entityClass, Long id) {
        super("Entity " + entityClass.getSimpleName() + " with id " + id + " already exists.");
    }

    public EntityAlreadyExistsException(Class<?> entityClass, String value) {
        super("Entity " + entityClass.getSimpleName() + " with value " + value + " already exists.");
    }
}
