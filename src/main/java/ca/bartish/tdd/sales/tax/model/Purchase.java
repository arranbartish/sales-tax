package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.javamoney.moneta.function.MonetaryOperators;

import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Data
@Builder
@EqualsAndHashCode
public final class Purchase {

    private final Item item;
    @Singular
    private final List<Duty> duties;

    private Money getItemCost() {
        return item.getPrice().multiply(item.getQuantity());
    }

    public Money getCost() {
        Money itemCost = getItemCost();

        return duties.stream()
                .map(duty -> itemCost.multiply(duty.getRate()))
                .reduce(itemCost, Money::add);

    }

    public Money getTax() {
        Money itemCost = getItemCost();

        return duties.stream()
                .map(duty -> itemCost.multiply(duty.getRate()))
                .reduce(Money.value("0.0").build(), Money::add);
    }
}
