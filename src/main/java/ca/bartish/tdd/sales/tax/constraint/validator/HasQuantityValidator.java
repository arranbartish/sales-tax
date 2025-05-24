package ca.bartish.tdd.sales.tax.constraint.validator;


import ca.bartish.tdd.sales.tax.constraint.HasQuantity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class HasQuantityValidator implements ConstraintValidator<HasQuantity, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(StringUtils::lowerCase)
                .map(this::selectQuantityComponent)
                .map(this::isNumber)
                .orElse(Boolean.TRUE);
    }

    public String selectQuantityComponent(String value) {
        return StringUtils.substringBefore(value, " ").trim();
    }

    public Boolean isNumber(String value) {
        return StringUtils.isNumeric(value);
    }
}