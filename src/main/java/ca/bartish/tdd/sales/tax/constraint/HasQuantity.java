package ca.bartish.tdd.sales.tax.constraint;

import ca.bartish.tdd.sales.tax.constraint.validator.HasQuantityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasQuantityValidator.class)
@Target({ ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface  HasQuantity {
    String message() default "{ca.bartish.tdd.sales.tax.constraint.HasQuantity.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
