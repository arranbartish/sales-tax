package ca.bartish.tdd.sales.tax.constraint;

import ca.bartish.tdd.sales.tax.constraint.validator.HasDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasDescriptionValidator.class)
@Target({ ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface HasDescription {
    String message() default "{ca.bartish.tdd.sales.tax.constraint.HasDescription.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}