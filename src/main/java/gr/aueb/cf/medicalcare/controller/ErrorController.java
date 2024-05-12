package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ErrorController class
 */

@ControllerAdvice
public class ErrorController {

    /**
     * Handle EntityNotFoundException
     * @param e EntityNotFoundException
     * @return  Response entity
     */
    @ExceptionHandler(value = { EntityAlreadyExistsException.class })
    public ResponseEntity<String> handleEntityNotFound(EntityAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handle UserNotFoundException
     * @param e UserNotFoundException
     * @return  Response entity
     */
    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handle Exception
     * @param e Exception
     * @return  Response entity
     */
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }


}
