package ca.bartish.tdd.sales.tax.model;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContextBuilder;
import java.math.BigDecimal;

public class MonetaryAmountBuilder {
    private BigDecimal value;
    private String currency = "CAD";

    public MonetaryAmountBuilder value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public MonetaryAmountBuilder value(String value) {
        return value(new BigDecimal(value));
    }

    public MonetaryAmountBuilder currency(String value) {
        this.currency = value;
        return this;
    }

    public MonetaryAmount build() {
        return Monetary.getAmountFactory(org.javamoney.moneta.Money.class)
                .setCurrency(currency)
                .setNumber(value).create();
    }
}
