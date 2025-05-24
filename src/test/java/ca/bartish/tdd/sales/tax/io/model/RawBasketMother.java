package ca.bartish.tdd.sales.tax.io.model;

import java.util.ArrayList;

public class RawBasketMother extends RawBasket.RawBasketBuilder {


    public RawBasketMother() {
        name("Basic basket")
        .item("1 book at 12.49")
        .item("1 imported box of chocolates at 10.00");
    }

}
