package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.*;
import ca.bartish.tdd.sales.tax.spi.DefaultTaxExemptionProvider;
import ca.bartish.tdd.sales.tax.spi.TaxExemptionProvider;

import java.util.List;
import java.util.ServiceLoader;

public class TaxHandlingOrderService implements OrderService {

    private final TaxResolver<List<Duty>> taxResolver;

    public TaxHandlingOrderService() {
        final TaxExemptionProvider exemptionProvider =
                ServiceLoader.load(TaxExemptionProvider.class).stream()
                        .findFirst()
                        .map(ServiceLoader.Provider::get)
                        .orElseGet(DefaultTaxExemptionProvider::new);


        taxResolver = new CompositeTaxResolver(List.of(
                new NamedTaxResolver("0.05", "Import Duty", Item::isImported),
                new NamedTaxResolver("0.1", "Provincial Duty", item -> !exemptionProvider.isExempt(item.getName()))
        ));
    }

    @Override
    public Receipt process(Basket basket) {
        Receipt.ReceiptBuilder receipt = Receipt.builder();
        basket.getItems()
                .stream()
                .map(item -> Purchase.builder().item(item)
                        .duties(taxResolver.resolve(item))
                        .build())
                .forEach(receipt::purchase);
        return receipt.build();
    }
}
