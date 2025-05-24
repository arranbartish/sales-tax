package ca.bartish.tdd.sales.tax.spi;

import java.util.Locale;

public class HardCodedTaxExemptionProvider implements TaxExemptionProvider {
    @Override
    public boolean isExempt(String itemDescription) {
        return itemDescription.toLowerCase(Locale.ENGLISH).contains("book") ||
                itemDescription.toLowerCase(Locale.ENGLISH).contains("chocolate") ||
                itemDescription.toLowerCase(Locale.ENGLISH).contains("pill");
    }
}
