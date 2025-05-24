package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import ca.bartish.tdd.sales.tax.model.Basket;

public class BasketMapper implements Mapper<RawBasket, Basket> {

    private final ItemMapper itemMapper = new ItemMapper();

    @Override
    public Basket map(RawBasket source) {
        Basket.BasketBuilder builder = Basket.builder();
        builder.name(source.getName());
        source.getItems().forEach(item -> {
            builder.item(itemMapper.map(item));
        });
        return builder.build();
    }
}
