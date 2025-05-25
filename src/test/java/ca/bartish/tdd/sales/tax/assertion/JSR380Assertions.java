package ca.bartish.tdd.sales.tax.assertion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;

import java.util.Set;

public class JSR380Assertions {

    public static <TargetT> JSR380Assert<TargetT> assertThat(TargetT actual) {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {;
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TargetT>> violations = validator.validate(actual);
            return new JSR380Assert<>(actual, violations);
        }
    }

}
