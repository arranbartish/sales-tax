package ca.bartish.tdd.sales.tax.io;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClasspathResourceReader implements FileReader<List<String>, String> {

    private final FileReader<List<String>, Path> pathReader = new PathReader();

    @Override
    public List<String> read(String source) {
        Path path = stringResourceToPath(source);
        return pathReader.read(path);
    }

    private Path stringResourceToPath(String resource) {
        try {
            URL url = ClasspathResourceReader.class.getClassLoader().getResource(resource);
            return Paths.get(url.toURI());
        } catch (Exception e) {
            throw new IllegalArgumentException("Provided resource could not be converted to path to be read", e);
        }
    }


}
