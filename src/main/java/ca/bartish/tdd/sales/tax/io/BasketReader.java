package ca.bartish.tdd.sales.tax.io;

import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class BasketReader implements FileReader<List<RawBasket>, Supplier<List<String>>> {

    private record BasketNameAndItems(int nameIndex, int lastItemIndex) {}

    @Override
    public List<RawBasket> read(Supplier<List<String>> source) {
        List<String> lines = source.get();
        List<Integer> basketNames = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).trim().endsWith(":"))
                .boxed()
                .toList();

        return IntStream.range(0, basketNames.size())
                .mapToObj(i -> {
                    int nameIndex = basketNames.get(i);
                    int lastItemIndex = (i + 1 < basketNames.size()) ? basketNames.get(i + 1) : lines.size();
                    return new BasketNameAndItems(nameIndex, lastItemIndex);
                })
                .map(basketIndexes -> extractBasket(lines, basketIndexes))
                .toList();

    }

    private RawBasket extractBasket(List<String> lines, BasketNameAndItems basketIndexes) {
        String name = StringUtils.remove(lines.get(basketIndexes.nameIndex), ":").trim();
        List<String> items = lines.subList(basketIndexes.nameIndex + 1, basketIndexes.lastItemIndex)
                .stream()
                .map(StringUtils::trimToNull)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNotBlank)
                .toList();

        return RawBasket.builder().name(name).items(items).build();

    }
}
