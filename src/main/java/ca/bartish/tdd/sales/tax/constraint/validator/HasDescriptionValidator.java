package ca.bartish.tdd.sales.tax.constraint.validator;


import ca.bartish.tdd.sales.tax.constraint.HasDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class HasDescriptionValidator implements ConstraintValidator<HasDescription, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(StringUtils::lowerCase)
                .map(this::removeImportedIndicator)
                .map(this::removePrice)
                .map(this::removeQuantity)
                .map(this::hasDescription)
                .orElse(Boolean.TRUE);
    }

    private String removeImportedIndicator(String value) {
        return StringUtils.remove(value, "imported ").trim();
    }

    private String removePrice(String value) {
        return StringUtils.substringBeforeLast(value, "at").trim();
    }

    private String removeQuantity(String value) {
        return StringUtils.substringAfter(value, " ").trim();
    }

    private Boolean hasDescription(String value) {
        return StringUtils.isNotBlank(value);
    }
}