package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.model.Item;

import java.util.List;
import java.util.stream.Stream;

public class LineItemMapper implements Mapper<String[], List<Item>> {

    private final Mapper<String, Item> itemMapper;

    public LineItemMapper(final Mapper<String, Item> itemMapper) {
        this.itemMapper = itemMapper;
    }

    public LineItemMapper() {
        this(new ItemMapper());
    }

    @Override
    public List<Item> map(String[] source) {
        return Stream.of(source)
                .map(itemMapper::map)
                .toList();
    }
}
