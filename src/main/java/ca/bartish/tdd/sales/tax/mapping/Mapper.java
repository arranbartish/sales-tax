package ca.bartish.tdd.sales.tax.mapping;

public interface Mapper<SourceT, TargetT> {
    TargetT map(SourceT source);
}
