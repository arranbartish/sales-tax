package ca.bartish.tdd.sales.tax;

import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import ca.bartish.tdd.sales.tax.model.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EqualityTest {

    @ParameterizedTest
    @ValueSource(classes = {
            Basket.class,
            Duty.class,
            Item.class,
            Money.class,
            Purchase.class,
            RawBasket.class,
            Receipt.class
    })
    void modelClassesMustMatchEqualsContract(Class<?> target) {
        EqualsVerifier.forClass(target).verify();
    }
}
