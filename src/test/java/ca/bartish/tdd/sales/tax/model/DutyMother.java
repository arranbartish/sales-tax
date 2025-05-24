package ca.bartish.tdd.sales.tax.model;

public class DutyMother extends Duty.DutyBuilder{

    public DutyMother() {
        super();
        provincial();
    }

    public DutyMother federal() {
        return (DutyMother) this
                .name("Federal Duty")
                .rateAsString("0.07");
    }

    public DutyMother provincial() {
        return (DutyMother) this
                .name("Provincial Duty")
                .rateAsString("0.10");
    }

    public DutyMother importTariff() {
        return (DutyMother) this
                .name("Import Duty")
                .rateAsString("0.05");
    }
}