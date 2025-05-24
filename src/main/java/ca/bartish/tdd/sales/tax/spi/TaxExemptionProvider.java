package ca.bartish.tdd.sales.tax.spi;

public interface TaxExemptionProvider {

    boolean isExempt(String itemDescription);

}
