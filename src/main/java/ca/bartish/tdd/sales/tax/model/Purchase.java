package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.javamoney.moneta.function.MonetaryOperators;

import java.math.BigDecimal;
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

        return itemCost.add(getTax());
    }

    public Money getTax() {
        Money itemCost = getItemCost();
        return duties.stream()
                .map(duty -> itemCost.multiply(duty.getRate()))
                .map(this::roundUpToNearest005)
                .reduce(Money.value("0.0").build(), Money::add);
    }

    private Money roundUpToNearest005(Money money) {
       BigDecimal value = new BigDecimal(money.valueAsString());
       BigDecimal divided = value.divide(new BigDecimal("0.05"), 0, RoundingMode.UP);
       BigDecimal rounded = divided.multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.UNNECESSARY);
       Money raw = Money.value(rounded).specificRoundingMode(RoundingMode.CEILING).build();
       return Money.value(raw.valueAsString()).build();
    }
}
