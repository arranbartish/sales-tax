package ca.bartish.tdd.sales.tax.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void willMeetContractForEquals() {
        EqualsVerifier.forClass(Money.class).verify();
    }
}