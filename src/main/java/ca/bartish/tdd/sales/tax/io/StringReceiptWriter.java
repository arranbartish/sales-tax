package ca.bartish.tdd.sales.tax.io;

import ca.bartish.tdd.sales.tax.model.Item;
import ca.bartish.tdd.sales.tax.model.Receipt;

import java.util.Optional;

public class StringReceiptWriter implements ReceiptWriter<String> {

    @Override
    public String write(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        receipt.getPurchases().forEach(purchase -> {
            sb.append(
                    String.format("%s %s: %s", purchase.getItem().getQuantity(), getDescription(purchase.getItem()), purchase.getCost().valueAsString()))
                    .append('\n');
        });
        sb.append(String.format("Sales Taxes: %s", receipt.getSalesTaxes().valueAsString())).append('\n');
        sb.append(String.format("Total: %s", receipt.getTotal().valueAsString())).append('\n');


        return sb.toString();
    }

    private String getDescription(Item item) {
        return Optional.of(item)
                .filter(Item::isImported)
                .map(it -> String.format("imported %s", it.getName()))
                .orElseGet(item::getName);
    }
}
