package ca.bartish.tdd.sales.tax.io;

import ca.bartish.tdd.sales.tax.model.DutyMother;
import ca.bartish.tdd.sales.tax.model.ItemMother;
import ca.bartish.tdd.sales.tax.model.Receipt;
import ca.bartish.tdd.sales.tax.model.ReceiptMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringReceiptWriterTest {

    private final String expected = """
            1 book: 12.49
            1 imported music CD: 16.49
            1 chocolate bar: 0.85
            Sales Taxes: 1.50
            Total: 29.83
            """;


    private final ReceiptWriter<String> sut = new StringReceiptWriter();

    @Test
    void willWriteReceipt() {
        Receipt receipt = new ReceiptMother()
                .cleanPurchases()
                .addPurchase(purchaseMother ->
                                purchaseMother
                                        .addItem(new ItemMother().book("12.49").build())
                                        .dutyFree()
                )
                .addPurchase(purchaseMother ->
                        purchaseMother
                                .cleanDuties()
                                .addItem(new ItemMother().musicCD("14.99").imported().build())
                                .addDuty(DutyMother::provincial)
                )
                .addPurchase(purchaseMother ->
                        purchaseMother
                                .addItem(new ItemMother().chocolateBar("0.85").build())
                                .dutyFree())
                .build();

        String actual = sut.write(receipt);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}