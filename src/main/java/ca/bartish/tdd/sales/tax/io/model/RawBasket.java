package ca.bartish.tdd.sales.tax.io.model;

import ca.bartish.tdd.sales.tax.constraint.HasDescription;
import ca.bartish.tdd.sales.tax.constraint.HasPrice;
import ca.bartish.tdd.sales.tax.constraint.HasQuantity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public final class RawBasket {
    private final String name;
    @Singular
    private final List<@HasDescription @HasPrice @HasQuantity @NotBlank @NotNull String> items;

    public static class RawBasketBuilder {

        public RawBasketBuilder emptyBasket() {
            this.items = new ArrayList<>();
            return this;
        }
    }
}
