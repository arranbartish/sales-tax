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
                        "The item in the basket [imported] must have a price and did not.",
                        "The item in the basket [imported] must have a quantity and did not.",
                        "The item in the basket [imported] must have a description and did not.");
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
                        "The item in the basket [ ] must have a quantity and did not.",
                        "must not be blank",
                        "The item in the basket [ ] must have a description and did not.",
                        "The item in the basket [ ] must have a price and did not.");
    }
}