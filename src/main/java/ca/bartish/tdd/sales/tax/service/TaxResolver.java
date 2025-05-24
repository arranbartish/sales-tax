package ca.bartish.tdd.sales.tax.service;

import ca.bartish.tdd.sales.tax.model.Item;

public interface TaxResolver<DutyContainerT> {

    DutyContainerT resolve(Item item);
}
