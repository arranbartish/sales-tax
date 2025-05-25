package ca.bartish.tdd.sales.tax.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class InputStreamReader implements FileReader<List<String>, InputStream> {

    @Override
    public List<String> read(InputStream source) {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(source))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read lines from InputStream", e);
        }
    }
}
