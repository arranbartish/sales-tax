package ca.bartish.tdd.sales.tax.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public final class Item {
    private final String name;
    private final Money price;
    private final boolean isImported;
    private final boolean isExempted;
    private final int quantity;


    public static class ItemBuilder {

        public ItemBuilder imported() {
            return isImported(true);
        }

        public ItemBuilder local() {
            return isImported(false);
        }

        public ItemBuilder taxFree() {
            return isExempted(true);
        }

        public ItemBuilder taxed() {
            return isExempted(false);
        }
    }
}
