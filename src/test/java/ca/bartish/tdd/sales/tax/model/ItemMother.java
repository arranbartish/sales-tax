package ca.bartish.tdd.sales.tax.model;

import java.util.function.Consumer;

public class ItemMother extends Item.ItemBuilder {


    private MoneyMother moneyMother = new MoneyMother().tenDollarsCanadian();

    public ItemMother() {
        super();
        book();
    }

    public ItemMother book() {
        return book("10.00");
    }

    public ItemMother book(String price) {
        return (ItemMother) price(m -> m.value(price))
                .name("book")
                .taxFree()
                .local()
                .quantity(1);
    }

    public ItemMother musicCD() {
        return musicCD("10.00");
    }

    public ItemMother musicCD(String price) {
        return (ItemMother) price(m -> m.value(price))
                .name("music CD")
                .taxed()
                .local()
                .quantity(1);
    }

    public ItemMother chocolateBar() {
        return chocolateBar("10.00");
    }


    public ItemMother chocolateBar(String price) {
        return (ItemMother) price(m -> m.value(price))
                .name("chocolate bar")
                .taxed()
                .local()
                .quantity(1);
    }

    public ItemMother price(String value) {
        return price(m -> m.value(value));
    }

    public Item.ItemBuilder price(Money value) {
        super.price(value);
        return price(m -> m.value(value));
    }


    public ItemMother price(Consumer<MoneyMother> priceCustomizer) {
        priceCustomizer.accept(moneyMother);
        return this;
    }

    @Override
    public Item build() {
        price(moneyMother.build());
        moneyMother = null;
        return super.build();
    }


}
