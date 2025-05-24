package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public final class Basket {

    private final String name;

    @Singular
    private final List<Item> items;
}
