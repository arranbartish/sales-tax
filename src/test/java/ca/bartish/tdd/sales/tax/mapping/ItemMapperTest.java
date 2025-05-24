package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.model.Item;
import ca.bartish.tdd.sales.tax.model.ItemMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemMapperTest {

    private static final String BOOK =  "1 book at 12.49";
    private static final String MUSIC_CDS =  "2 music CD at 14.99";
    private static final String CHOCOLATES =  "1 imported chocolates at 5.30";

    private final Mapper<String, Item> sut = new ItemMapper();

    @Test
    void willMapABook() {
        Item book = sut.map(ItemMapperTest.BOOK);
        Item expected = new ItemMother()
                .book("12.49")
                .taxFree()
                .local()
                .build();
        Assertions.assertThat(book).isEqualTo(expected);
    }

    @Test
    void willMap2TaxedCDs() {
        Item book = sut.map(ItemMapperTest.MUSIC_CDS);
        Item expected = new ItemMother()
                .musicCD("14.99")
                .quantity(2)
                .taxed()
                .local()
                .build();
        Assertions.assertThat(book).isEqualTo(expected);
    }

    @Test
    void willMap1UntaxedImportedChocolates() {
        Item book = sut.map(ItemMapperTest.CHOCOLATES);
        Item expected = new ItemMother()
                .chocolateBar("5.30")
                .name("chocolates")
                .quantity(1)
                .taxFree()
                .imported()
                .build();
        Assertions.assertThat(book).isEqualTo(expected);
    }

}