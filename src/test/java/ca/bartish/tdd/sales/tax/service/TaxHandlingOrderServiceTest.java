package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxHandlingOrderServiceTest {

    final OrderService sut = new TaxHandlingOrderService();

    @Test
    void willPurchaseBasketFor2ItemsNoTaxes() {
        Basket basket = new BasketMother()
                .empty()
                .addItem(itemMother ->
                        itemMother.book("12.49")
                                .local()
                                .quantity(2))
                .build();

        Receipt expected = new ReceiptMother()
                .cleanPurchases()
                .addPurchase(purchaseMother ->
                        purchaseMother
                                .addItem(basket.getItems().stream().findFirst().orElseThrow())
                                .dutyFree())
                .build();

        Receipt actual = sut.process(basket);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willPurchaseWithAllTaxes() {
        Basket basket = new BasketMother()
                .empty()
                .addItem(itemMother ->
                        itemMother.price("12.49")
                                .name("Perfume")
                                .imported()
                                .quantity(2))
                .build();

        Receipt expected = new ReceiptMother()
                .cleanPurchases()
                .addPurchase(purchaseMother ->
                        purchaseMother
                                .cleanDuties()
                                .addItem(basket.getItems().stream().findFirst().orElseThrow())
                                .addDuty(DutyMother::importTariff)
                                .addDuty(DutyMother::provincial))
                .build();

        Receipt actual = sut.process(basket);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}