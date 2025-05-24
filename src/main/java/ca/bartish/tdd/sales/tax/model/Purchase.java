package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public final class Purchase {

    private final Item item;
    @Singular
    private final List<Duty> duties;
}
