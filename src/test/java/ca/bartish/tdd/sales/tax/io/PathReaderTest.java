package ca.bartish.tdd.sales.tax.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PathReaderTest {
    private Path path;

    @BeforeEach
    void setUp() throws Exception {
        URL resource = PathReaderTest.class.getClassLoader().getResource("example/baskets.dat");
        path = Paths.get(resource.toURI());
    }

    private final FileReader<List<String>, Path> sut = new PathReader();

    @Test
    void willReadPath() {
        List<String> actual = sut.read(path);
        Assertions.assertThat(actual)
                .contains("1 book at 12.49",
                        "1 music CD at 14.99",
                        "1 chocolate bar at 0.85");
    }

    @Test
    void willHandleBadConfig() {
        Assertions.assertThatThrownBy(() -> sut.read(Paths.get("does/not/exist")))
                .hasMessageContaining("The path provided could not be read");
    }
}