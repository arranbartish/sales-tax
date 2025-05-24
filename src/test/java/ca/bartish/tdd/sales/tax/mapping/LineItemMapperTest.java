package ca.bartish.tdd.sales.tax.mapping;

import ca.bartish.tdd.sales.tax.model.Item;
import ca.bartish.tdd.sales.tax.model.ItemMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LineItemMapperTest {

    private static final String[] TEST_INPUT_ONE =
            {
                    "1 book at 12.49",
                    "1 music CD at 14.99",
                    "1 chocolate bar at 0.85"
            };

    private Mapper<String[], List<Item>> sut = new LineItemMapper();

    @Test
    void will_map_items(){
        List<Item> items = sut.map(TEST_INPUT_ONE);
        Assertions.assertThat(items)
                .containsExactly(
                        new ItemMother().book("12.49").build(),
                        new ItemMother().musicCD("14.99").build(),
                        new ItemMother().chocolateBar("0.85").taxFree().build());

    }


}
