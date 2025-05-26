package ca.bartish.tdd.sales.tax;

import lombok.Builder;
import lombok.Data;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ApplicationTest {

    public static Properties properties = ApplicationTest.getApplicationTestProperties();

    public static String EXPECTED_EXAMPLE = """
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

    @ParameterizedTest
    @MethodSource("executions")
    void checkExecution(ApplicationExecution execution) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);

        SoftAssertions.assertSoftly(softly -> {

            Application.applicationFactory = () -> new Application(printStream);
            Application.applicationExitFactory = statusCode -> softly.assertThat(statusCode)
                    .describedAs(execution.getExpectedExitCodeViolationMessage())
                    .isEqualTo(execution.getExpectedExitCode());

            Application.main(execution.getParameters().get());

            String outputString = output.toString();
            softly.assertThat(outputString).isEqualTo(execution.getExpectedResult());
        });
    }

    private static Stream<Arguments> executions() {

        return Stream.of(
                Arguments.of(ApplicationExecution.builder()
                        .expectedExitCode(0)
                        .expectedExitCodeViolationMessage("Application must exit successfully and didn't")
                        .expectedResult(EXPECTED_EXAMPLE)
                        .parameters(() -> new String[]{}).build()),
                Arguments.of(ApplicationExecution.builder()
                        .expectedExitCode(0)
                        .expectedExitCodeViolationMessage("Application must exit successfully and didn't")
                        .expectedResult(EXPECTED_EXAMPLE)
                        .parameters(() -> new String[]{properties.getProperty("example.file")}).build()),
                Arguments.of(ApplicationExecution.builder()
                        .expectedExitCode(2)
                        .expectedExitCodeViolationMessage("Application must exit with a user error and didn't")
                        .expectedResult(INVALID_FEEDBACK)
                        .parameters(() -> new String[]{properties.getProperty("invalid.file")}).build())
        );
    }

    @Data
    @Builder
    static final class ApplicationExecution {

        private final Supplier<String[]> parameters;
        private final String expectedResult;
        private final Integer expectedExitCode;
        private final String expectedExitCodeViolationMessage;
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
