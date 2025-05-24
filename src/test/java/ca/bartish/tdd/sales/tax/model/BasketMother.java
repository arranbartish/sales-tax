package ca.bartish.tdd.sales.tax.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BasketMother extends Basket.BasketBuilder {

    private List<ItemMother> items;

    public BasketMother() {
        items = new ArrayList<>();
        items.add(new ItemMother().book());
        name("A basket");
    }

    public BasketMother empty() {
        items.clear();
        return this;
    }

    public BasketMother addItem(Consumer<ItemMother> itemCustomizer) {
        ItemMother item = new ItemMother();
        itemCustomizer.accept(item);
        items.add(item);
        return this;
    }

    @Override
    public Basket build() {
        items.stream()
                .map(ItemMother::build)
                .forEach(super::item);
        items = null;
        return super.build();
    }



}
