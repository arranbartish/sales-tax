package ca.bartish.tdd.sales.tax.constraint;

import ca.bartish.tdd.sales.tax.constraint.validator.HasPriceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasPriceValidator.class)
@Target({ ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPrice {
    String message() default "{ca.bartish.tdd.sales.tax.constraint.HasPrice.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
