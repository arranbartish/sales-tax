package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.model.Item;

import java.util.List;
import java.util.stream.Stream;

public class InputMapper implements Mapper<String[], List<Item>> {

    private final Mapper<String, Item> itemMapper;

    public InputMapper(final Mapper<String, Item> itemMapper) {
        this.itemMapper = itemMapper;
    }

    public InputMapper() {
        this(new ItemMapper());
    }

    @Override
    public List<Item> map(String[] source) {
        return Stream.of(source)
                .map(itemMapper::map)
                .toList();
    }
}
