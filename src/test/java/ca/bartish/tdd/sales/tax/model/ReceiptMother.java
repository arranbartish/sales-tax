package ca.bartish.tdd.sales.tax.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReceiptMother extends Receipt.ReceiptBuilder {

    private List<PurchaseMother> purchases;

    public ReceiptMother() {
        this.purchases = new ArrayList<>();
        purchases.add(new PurchaseMother());
    }

    public ReceiptMother cleanPurchases() {
        purchases.clear();
        super.clearPurchases();
        return this;
    }

    public ReceiptMother addPurchase(Consumer<PurchaseMother> purchaseCustomizer) {
        PurchaseMother purchase = new PurchaseMother();
        purchaseCustomizer.accept(purchase);
        purchases.add(purchase);
        return this;
    }

    @Override
    public Receipt build() {
        purchases.stream()
                .map(PurchaseMother::build)
                .forEach(super::purchase);
        purchases = null;
        return super.build();
    }
}
