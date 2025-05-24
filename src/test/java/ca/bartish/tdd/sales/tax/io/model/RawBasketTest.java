package ca.bartish.tdd.sales.tax.io.model;

import ca.bartish.tdd.sales.tax.assertion.JSR380Assertions;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RawBasketTest {

    @Test
    void willMatchContractForEquals() {
        EqualsVerifier.forClass(RawBasket.class).verify();
    }

    @Test
    void willBeValid() {
        RawBasket basket = new RawBasketMother()
                .emptyBasket()
                .name("valid")
                .item("1 book at 12.49")
                .build();
        JSR380Assertions.assertThat(basket).isValid();
    }

    @Test
    void willNotBeValidWhenAValueIsProvided() {
        RawBasket basket = new RawBasketMother()
                .emptyBasket()
                .name("invalid item")
                .item("imported")
                .build();
        JSR380Assertions.assertThat(basket)
                .hasViolationsExactlyInAnyOrder(
                        "{ca.bartish.tdd.sales.tax.constraint.HasDescription.message}",
                        "{ca.bartish.tdd.sales.tax.constraint.HasPrice.message}",
                        "{ca.bartish.tdd.sales.tax.constraint.HasQuantity.message}");
    }

    @Test
    void willNotBeValidWhenEmptyProvided() {
        RawBasket basket = new RawBasketMother()
                .emptyBasket()
                .name("empty item")
                .item(" ")
                .build();
        JSR380Assertions.assertThat(basket)
                .hasViolationsExactlyInAnyOrder(
                        "{ca.bartish.tdd.sales.tax.constraint.HasDescription.message}",
                        "{ca.bartish.tdd.sales.tax.constraint.HasPrice.message}",
                        "{ca.bartish.tdd.sales.tax.constraint.HasQuantity.message}",
                                "must not be blank");
    }
}