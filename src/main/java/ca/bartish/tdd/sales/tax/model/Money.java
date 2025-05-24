package ca.bartish.tdd.sales.tax.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public final class Money {

    private final MonetaryAmount value;

    private Money(MonetaryAmount value) {
        this.value = value;
    }

    public Money multiply(Number multiplier) {
        return new Money(value.multiply(multiplier));
    }

    public static MoneyBuilder value(String value) {
        return new MoneyBuilder().value(value);
    }

    public static class MoneyBuilder {

        private BigDecimal value;
        private String currency = "CAD";

        public MoneyBuilder value(String value) {
            this.value = new BigDecimal(value);
            return this;
        }

        public MoneyBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Money build() {
            MonetaryAmount moneyAmount = new MonetaryAmountBuilder().value(value).currency(currency).build();
            return new Money(moneyAmount);
        }
    }
}
