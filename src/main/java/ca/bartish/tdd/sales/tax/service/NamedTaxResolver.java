package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.Duty;
import ca.bartish.tdd.sales.tax.model.Item;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

public class NamedTaxResolver implements TaxResolver<Optional<Duty>> {

    private final BigDecimal rate;
    private final String name;
    private final Predicate<Item> applicationStrategy;

    public NamedTaxResolver(final String rate, final String name, final Predicate<Item> applicationStrategy) {
        this.rate = new BigDecimal(rate);
        this.name = name;
        this.applicationStrategy = applicationStrategy;
    }

    @Override
    public Optional<Duty> resolve(Item item) {
        return Optional.of(item)
                .filter(applicationStrategy)
                .map(it -> Duty.builder()
                        .name(name)
                        .rate(rate)
                        .build());
    }
}
