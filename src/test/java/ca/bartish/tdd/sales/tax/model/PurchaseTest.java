package ca.bartish.tdd.sales.tax.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PurchaseTest {

    @Test
    void willGetCostWhenNoDutiesApply() {
        Purchase purchase = new PurchaseMother().cleanDuties()
                .item(customizer -> customizer
                        .book("12.49")
                        .local()
                        .quantity(2)).build();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(purchase.getCost())
                    .isEqualTo(Money.value("24.98").build());
            softly.assertThat(purchase.getTax())
                    .isEqualTo(Money.value("0.0").build());
        });

    }

    @Test
    void willGetCostWhenProvincialDutiesApply() {
        Purchase purchase = new PurchaseMother().cleanDuties()
                .item(customizer -> customizer
                        .book("12.49")
                        .local()
                        .quantity(1))
                .addDuty(DutyMother::provincial)
                .build();



        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(purchase.getCost())
                    .isEqualTo(Money.value("13.74").build());
            softly.assertThat(purchase.getTax())
                    .isEqualTo(Money.value("1.25").build());
        });
    }

    @Test
    void willGetCostWhenImportAndProvincialDutiesApply() {
        Purchase purchase = new PurchaseMother().cleanDuties()
                .item(customizer -> customizer
                        .book("12.49")
                        .local()
                        .quantity(1))
                .addDuty(DutyMother::provincial)
                .addDuty(DutyMother::importTariff)
                .build();


        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(purchase.getCost())
                    .isEqualTo(Money.value("14.39").build());
            softly.assertThat(purchase.getTax())
                    .isEqualTo(Money.value("1.90").build());
        });
    }

    @Test
    void willRoundUpTaxToNearest0point05() {
        Purchase purchase = new PurchaseMother()
                .cleanDuties()
                .item(customizer -> customizer
                        .price("47.50")
                        .name("bottle of perfume")
                        .imported()
                        .quantity(1))
                .addDuty(DutyMother::provincial)
                .addDuty(DutyMother::importTariff)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(purchase.getCost())
                    .isEqualTo(Money.value("54.65").build());
            softly.assertThat(purchase.getTax())
                    .isEqualTo(Money.value("7.15").build());
        });


    }
}