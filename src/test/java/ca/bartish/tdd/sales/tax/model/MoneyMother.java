package ca.bartish.tdd.sales.tax.model;

public class MoneyMother extends Money.MoneyBuilder {


    public MoneyMother tenDollarsCanadian() {
        return (MoneyMother)value("10.00").currency("CAD");
    }

    public MoneyMother tenDollarsUS() {
        return (MoneyMother)value("10.00").currency("USD");
    }

    public MoneyMother tenDollarsAustralian() {
        return (MoneyMother)value("10.00").currency("AUD");
    }
}
