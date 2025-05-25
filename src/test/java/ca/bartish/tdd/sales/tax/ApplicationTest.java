package ca.bartish.tdd.sales.tax;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    public static Properties properties = ApplicationTest.getApplicationTestProperties();

    public String expectedExample = """
            Output 1:
            1 book: 12.49
            1 music CD: 16.49
            1 chocolate bar: 0.85
            Sales Taxes: 1.50
            Total: 29.83
            
            Output 2:
            1 imported box of chocolates: 10.50
            1 imported bottle of perfume: 54.65
            Sales Taxes: 7.65
            Total: 65.15
            
            Output 3:
            1 imported bottle of perfume: 32.19
            1 bottle of perfume: 20.89
            1 packet of headache pills: 9.75
            1 imported box of chocolates: 11.85
            Sales Taxes: 6.70
            Total: 74.68
            
            """;

    private static final String INVALID_FEEDBACK = """
            Basket 1 (Input 1) was invalid:
            The item in the basket [1 at 12.49] must have a description and did not.
            The item in the basket [1 music CD at] must have a price and did not.
            Basket 2 (Input 2) was invalid:
            The item in the basket [imported] must have a description and did not.
            The item in the basket [imported] must have a price and did not.
            The item in the basket [imported] must have a quantity and did not.
            Basket 3 (Input 3) was invalid:
            The item in the basket [9.75] must have a description and did not.
            The item in the basket [9.75] must have a price and did not.
            The item in the basket [9.75] must have a quantity and did not.
            """;


    @Test
    void willExecuteWithoutParameters() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);

        Application.applicationFactory = () -> new Application(printStream);
        Application.applicationExitFactory = statusCode -> assertThat(statusCode)
                .describedAs("Application must exit successfully and didn't")
                .isEqualTo(0);

        Application.main(new String[]{});

        String outputString = output.toString();
        Assertions.assertThat(outputString).isEqualTo(expectedExample);
    }

    @Test
    void willExecuteWithAFileParameter() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);

        Application.applicationFactory = () -> new Application(printStream);
        Application.applicationExitFactory = statusCode -> assertThat(statusCode)
                .describedAs("Application must exit successfully and didn't")
                .isEqualTo(0);

        Application.main(new String[]{properties.getProperty("example.file")});

        String outputString = output.toString();
        Assertions.assertThat(outputString).isEqualTo(expectedExample);
    }

    @Test
    void willExecuteAndProvideFeedback() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);

        SoftAssertions.assertSoftly(softly -> {

            Application.applicationFactory = () -> new Application(printStream);
            Application.applicationExitFactory = statusCode -> softly.assertThat(statusCode)
                    .describedAs("Application must exit successfully and didn't")
                    .isEqualTo(2);

            Application.main(new String[]{properties.getProperty("invalid.file")});

            String outputString = output.toString();
            softly.assertThat(outputString).isEqualTo(INVALID_FEEDBACK);
        });


    }

    static Properties getApplicationTestProperties() {
        Properties properties = new Properties();
        try (InputStream input = ApplicationTest.class.getClassLoader().getResourceAsStream("fixture.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("fixture.properties file not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("fixture.properties failed to be read", e);
        }
        return properties;
    }
}
