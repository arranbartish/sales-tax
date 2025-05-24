package ca.bartish.tdd.sales.tax.io;

public interface FileReader <ResultT, SourceT> {

    ResultT read(SourceT source);

}
