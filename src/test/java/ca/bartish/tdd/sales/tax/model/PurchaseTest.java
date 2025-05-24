package ca.bartish.tdd.sales.tax.model;

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
                    .isEqualTo(Money.value("14.36").build());
            softly.assertThat(purchase.getTax())
                    .isEqualTo(Money.value("1.87").build());
        });
    }

}