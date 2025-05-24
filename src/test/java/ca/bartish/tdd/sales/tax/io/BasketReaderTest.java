package ca.bartish.tdd.sales.tax.io;

import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import ca.bartish.tdd.sales.tax.io.model.RawBasketMother;
import ca.bartish.tdd.sales.tax.model.Basket;
import ca.bartish.tdd.sales.tax.model.BasketMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

class BasketReaderTest {

    private final FileReader<List<RawBasket>, Supplier<List<String>>> sut =
            new BasketReader();

    @Test
    void willReadBaskets() {
        List<RawBasket> actual = sut.read(() -> List.of(
                "Input 1:",
                "1 book at 12.49",
                "",
                "Input 2:",
                "1 imported box of chocolates at 10.00"));
        Assertions.assertThat(actual)
                .containsExactly(
                        new RawBasketMother().emptyBasket()
                                .name("Input 1")
                                .item("1 book at 12.49")
                                .build(),
                        new RawBasketMother().emptyBasket()
                                .name("Input 2")
                                .item("1 imported box of chocolates at 10.00")
                                .build());
    }
}