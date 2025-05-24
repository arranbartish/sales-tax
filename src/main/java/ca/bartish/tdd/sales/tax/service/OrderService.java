package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.Basket;
import ca.bartish.tdd.sales.tax.model.Purchase;
import ca.bartish.tdd.sales.tax.model.Receipt;

public interface OrderService {

    Receipt process(Basket basket);
}
