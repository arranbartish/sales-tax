package ca.bartish.tdd.sales.tax.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PathReader implements FileReader<List<String>, Path> {

    @Override
    public List<String> read(Path source) {
        try {
            return Files.readAllLines(source);
        } catch (IOException e) {
            throw new IllegalArgumentException("The path provided could not be read", e);
        }
    }
}
