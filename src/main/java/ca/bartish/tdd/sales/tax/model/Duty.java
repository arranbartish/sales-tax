package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode
public final class Duty {

    private final String name;
    private final BigDecimal rate;

    @EqualsAndHashCode.Include(replaces = "rate")
    private BigDecimal removeRateZeros() {
        return rate == null ? null : rate.stripTrailingZeros();
    }

    public static class DutyBuilder {

        public DutyBuilder rateAsString(String rate) {
            return rate(new BigDecimal(rate));
        }
    }

}
