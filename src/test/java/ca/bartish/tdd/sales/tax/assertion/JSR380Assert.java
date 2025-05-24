package ca.bartish.tdd.sales.tax.assertion;

import jakarta.validation.ConstraintViolation;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Set;

public class JSR380Assert<ActualT> extends AbstractAssert<JSR380Assert<ActualT>, ActualT> {

    private final ActualT actual;
    private final Set<ConstraintViolation<ActualT>> violations;

    JSR380Assert(ActualT actual, Set<ConstraintViolation<ActualT>> violations) {
        super(actual, JSR380Assert.class);
        this.actual = actual;
        this.violations = violations;
    }


    public JSR380Assert<ActualT> isValid() {
        if (!violations.isEmpty()) {
            failWithMessage("Expected object to be valid, but found %d violations: %s", violations.size(), violations);
        }
        return this;
    }

    public void hasViolationsExactlyInAnyOrder(String... violationMessages) {
        Assertions.assertThat(violations)
                .extracting(v -> v.getMessage())
                .containsExactlyInAnyOrderElementsOf(List.of(violationMessages));
    }
}
