package ca.bartish.tdd.sales.tax.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PurchaseMother extends Purchase.PurchaseBuilder {

    private ItemMother item = new ItemMother();
    private List<DutyMother> duties;

    public PurchaseMother() {
        duties = new ArrayList<>();
        duties.add(new DutyMother().provincial());
    }

    public PurchaseMother cleanDuties() {
        super.clearDuties();
        duties.clear();
        return this;
    }

    public PurchaseMother dutyFree() {
        return cleanDuties();
    }

    public PurchaseMother addItem(Item item) {
        return item(itemMother -> itemMother
                .isImported(item.isImported())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice()));

    }

    public PurchaseMother item(Consumer<ItemMother> itemCustomizer) {
        itemCustomizer.accept(item);
        return this;
    }

    public PurchaseMother addDuty(Consumer<DutyMother> dutyCustomizer) {
        DutyMother duty = new DutyMother();
        dutyCustomizer.accept(duty);
        duties.add(duty);
        return this;
    }

    @Override
    public Purchase build() {
        duties.stream()
                .map(DutyMother::build)
                .forEach(super::duty);
        super.item(item.build());
        item = null;
        duties = null;
        return super.build();
    }
}