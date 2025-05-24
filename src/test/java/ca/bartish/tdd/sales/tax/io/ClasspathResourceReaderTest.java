package ca.bartish.tdd.sales.tax.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

class ClasspathResourceReaderTest {

    private final FileReader<List<String>, String> sut = new ClasspathResourceReader();

    @Test
    void willReadPath() {
        List<String> actual = sut.read("example/baskets.dat");
        Assertions.assertThat(actual)
                .contains("1 book at 12.49",
                        "1 music CD at 14.99",
                        "1 chocolate bar at 0.85");
    }

    @Test
    void willHandleBadConfig() {
        Assertions.assertThatThrownBy(() -> sut.read("does/not/exist"))
                .hasMessageContaining("Provided resource could not be converted to path to be read");
    }
}