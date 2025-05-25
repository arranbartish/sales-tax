package ca.bartish.tdd.sales.tax.io;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClasspathResourceReader implements FileReader<List<String>, String> {

    private final FileReader<List<String>, InputStream> streamReader = new InputStreamReader();

    @Override
    public List<String> read(String source) {
        try (InputStream resourceAsStream = ClasspathResourceReader.class.getClassLoader().getResourceAsStream(source)) {
            return streamReader.read(resourceAsStream);
        } catch (Exception e) {
            throw new IllegalArgumentException("Provided resource could not be converted to path to be read", e);
        }
    }

}
