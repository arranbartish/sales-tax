package ca.bartish.tdd.sales.tax.spi;

import java.util.Map;

public class HardCodedMonetaryConfig implements org.javamoney.moneta.spi.MonetaryConfigProvider {

    private final Map<String, String> config = Map.of(
            "org.javamoney.moneta.Money.defaults.precision", "5",
            "org.javamoney.moneta.Money.defaults.mathContext", "DECIMAL64",
            "org.javamoney.moneta.Money.defaults.roundingMode", "HALF_UP"
    );

    @Override
    public Map<String, String> getProperties() {
        return Map.copyOf(config);
    }

}
