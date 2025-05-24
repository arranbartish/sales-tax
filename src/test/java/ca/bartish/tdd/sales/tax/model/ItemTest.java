package ca.bartish.tdd.sales.tax.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void ItemSupportsEqualsContract() {
        EqualsVerifier.forClass(Item.class).verify();
    }

}