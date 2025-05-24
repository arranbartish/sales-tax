package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public final class Receipt {

    @Singular
    private final List<Purchase> purchases;

    public Money getSalesTaxes() {
        return purchases.stream()
                .map(Purchase::getTax)
                .reduce(Money.value("0.0").build(), Money::add);
    }

    public Money getTotal() {
        return purchases.stream()
                .map(Purchase::getCost)
                .reduce(Money.value("0.0").build(), Money::add);
    }
}
