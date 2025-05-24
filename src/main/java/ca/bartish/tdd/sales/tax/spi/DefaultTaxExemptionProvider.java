package ca.bartish.tdd.sales.tax.spi;

public class DefaultTaxExemptionProvider implements TaxExemptionProvider {

    @Override
    public boolean isExempt(String itemDescription) {
        return false;
    }
}
