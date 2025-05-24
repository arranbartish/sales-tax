package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import ca.bartish.tdd.sales.tax.io.model.RawBasketMother;
import ca.bartish.tdd.sales.tax.model.Basket;
import ca.bartish.tdd.sales.tax.model.BasketMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketMapperTest {

    final BasketMapper sut = new BasketMapper();

    @Test
    void willMapARawBasket() {
        RawBasket rawBasket = new RawBasketMother().build();
        Basket expected = new BasketMother()
                .empty()
                .addItem(itemMother ->
                        itemMother.book("12.49")
                                .local()
                                .taxFree()
                                .quantity(1))
                .addItem(itemMother ->
                        itemMother.chocolateBar("10.00")
                        .imported()
                        .taxFree()
                        .quantity(1)
                        .name("box of chocolates"))
                .name(rawBasket.getName())
                .build();

        Basket actual = sut.map(rawBasket);
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
}