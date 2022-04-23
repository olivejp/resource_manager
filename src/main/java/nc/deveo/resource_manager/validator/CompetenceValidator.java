package nc.deveo.resource_manager.validator;


import nc.deveo.resource_manager.domain.Competence;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class CompetenceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Competence.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(target);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                errors.rejectValue(violation.getPropertyPath().toString(), "AIE", violation.getMessage());
            }
        }
    }
}
