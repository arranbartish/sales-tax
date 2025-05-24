package ca.bartish.tdd.sales.tax.io;

import ca.bartish.tdd.sales.tax.model.Receipt;

public interface ReceiptWriter<OutputT> {

    OutputT write(Receipt receipt);

}
