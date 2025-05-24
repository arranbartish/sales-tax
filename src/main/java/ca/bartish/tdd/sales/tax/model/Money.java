package ca.bartish.tdd.sales.tax.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javamoney.moneta.function.MonetaryOperators;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class Money {

    private final MonetaryAmount value;

    Money(MonetaryAmount value) {
        this.value = MonetaryOperators.rounding(RoundingMode.HALF_UP,2).apply(value);
    }

    public Money multiply(Number multiplier) {
        return new Money(value.multiply(multiplier));
    }

    public Money add(Money money) {
        return new Money(value.add(money.value));
    }

    public String valueAsString() {
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat("SIMPLE");
        return format.format(value);
    }

    public static MoneyBuilder value(String value) {
        return new MoneyBuilder().value(value);
    }
    public static class MoneyBuilder {

        private Money source;
        private BigDecimal value;
        private MonetaryAmount rawValue;
        private String currency = "CAD";

        public MoneyBuilder value(Money value) {
            this.source = value;
            return this;
        }

        public MoneyBuilder value(MonetaryAmount value) {
            this.rawValue = value;
            return this;
        }

        public MoneyBuilder value(String value) {
            this.value = new BigDecimal(value);
            return this;
        }

        public MoneyBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Money build() {
            return Optional.ofNullable(source)
                    .or(this::makeMoney)
                    .orElseThrow(() -> new IllegalStateException("Money could not be constructed from value and currency"));
        }

        private Optional<Money> makeMoney() {
            return Optional.ofNullable(rawValue)
                    .or(() -> Optional.of(new MonetaryAmountBuilder().value(value).currency(currency).build()))
                    .map(Money::new);
        }
    }
}
