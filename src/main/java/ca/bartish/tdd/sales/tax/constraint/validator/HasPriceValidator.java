package ca.bartish.tdd.sales.tax.constraint.validator;


import ca.bartish.tdd.sales.tax.constraint.HasPrice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

public class HasPriceValidator implements ConstraintValidator<HasPrice, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(StringUtils::lowerCase)
                .map(this::selectPriceComponent)
                .map(this::isNumber)
                .orElse(Boolean.TRUE);
    }

    public String selectPriceComponent(String value) {
        return StringUtils.substringAfterLast(value, "at").trim();
    }

    public Boolean isNumber(String value) {
        return NumberUtils.isCreatable(value);
    }
}