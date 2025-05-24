package ca.bartish.tdd.sales.tax.spi;

public class HardCodedTaxExemptionProvider implements TaxExemptionProvider {
    @Override
    public boolean isExempt(String itemDescription) {
        return itemDescription.contains("book") ||
                itemDescription.contains("chocolate") ||
                itemDescription.contains("pill");
    }
}
