package nc.deveo.resource_manager.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandlerRestController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ContraintsValidationError handleValidationExceptions(ConstraintViolationException ex) {
        var errors = new ContraintsValidationError();
        errors.setType("ContraintsValidationError");
        String errorsStr = "";
        for (var error : ex.getConstraintViolations()) {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errorsStr = String.format("%s %s %s", fieldName, errorMessage, System.lineSeparator());
        }
        errors.setError(errorsStr);
        return errors;
    }
}
