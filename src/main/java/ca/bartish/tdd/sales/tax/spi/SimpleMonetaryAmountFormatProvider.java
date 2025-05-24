package ca.bartish.tdd.sales.tax.spi;

import org.javamoney.moneta.format.MonetaryAmountDecimalFormat;
import org.javamoney.moneta.function.MoneyProducer;

import javax.money.Monetary;
import javax.money.format.AmountFormatQuery;
import javax.money.format.MonetaryAmountFormat;
import javax.money.spi.MonetaryAmountFormatProviderSpi;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class SimpleMonetaryAmountFormatProvider implements MonetaryAmountFormatProviderSpi {


    private MonetaryAmountFormat getAmountFormat(AmountFormatQuery query) {

        return new MonetaryAmountDecimalFormat(
                new DecimalFormat("#0.00"),
                new MoneyProducer(),
                Monetary.getCurrency("CAD"));
    }

    @Override
    public Set<String> getAvailableFormatNames() {
        return Set.of("SIMPLE");
    }

    @Override
    public Set<Locale> getAvailableLocales() {
        return Collections.singleton(Locale.getDefault());
    }

    @Override
    public String getProviderName() {
        return "SIMPLE";
    }

    @Override
    public Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery query) {
        return Collections.singleton(getAmountFormat(query));
    }
}