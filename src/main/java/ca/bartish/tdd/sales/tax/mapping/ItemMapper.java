package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.model.Item;
import ca.bartish.tdd.sales.tax.model.Money;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class ItemMapper implements Mapper<String, Item> {

    @Override
    public Item map(String source) {

        String[] countWithItemAndPrice = StringUtils.splitByWholeSeparator(source, "at");
        String priceAsString = countWithItemAndPrice[countWithItemAndPrice.length-1].trim();
        String countWithItem = removePrice(source, priceAsString);
        String[] countAndItem = StringUtils.split(countWithItem, " ");
        String itemName = IntStream.range(1, countAndItem.length)
                .mapToObj(i -> countAndItem[i])
                .reduce("", (left, right) -> String.format("%s %s", left, right))
                .trim();
        Money price = Money.value(countWithItemAndPrice[countWithItemAndPrice.length-1].trim()).build();
        boolean isImported = itemName.startsWith("imported");
        itemName = StringUtils.removeStart(itemName, "imported").trim();
        return Item.builder()
                .isImported(isImported)
                .name(itemName)
                .quantity(Integer.parseInt(countAndItem[0]))
                .price(price)
                .build();
    }

    private String removePrice(String source, String priceAsString) {
        String remainingWithAt = StringUtils.remove(source, priceAsString).trim();
        return StringUtils.removeEnd(remainingWithAt, "at").trim();
    }
}
