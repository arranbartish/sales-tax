package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.Duty;
import ca.bartish.tdd.sales.tax.model.Item;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CompositeTaxResolver implements TaxResolver<List<Duty>>{

    private final List<TaxResolver<Optional<Duty>>> taxResolvers;

    public CompositeTaxResolver(final List<TaxResolver<Optional<Duty>>> taxResolvers) {
        this.taxResolvers = taxResolvers;
    }

    @Override
    public List<Duty> resolve(Item item) {
        return taxResolvers.stream()
                .map(resolver -> resolver.resolve(item))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(Duty::getName))
                .toList();
    }
}
