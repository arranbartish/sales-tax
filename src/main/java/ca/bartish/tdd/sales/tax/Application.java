package ca.bartish.tdd.sales.tax;

import ca.bartish.tdd.sales.tax.io.*;
import ca.bartish.tdd.sales.tax.io.model.RawBasket;
import ca.bartish.tdd.sales.tax.mapping.BasketMapper;
import ca.bartish.tdd.sales.tax.mapping.Mapper;
import ca.bartish.tdd.sales.tax.model.Basket;
import ca.bartish.tdd.sales.tax.service.OrderService;
import ca.bartish.tdd.sales.tax.service.TaxHandlingOrderService;
import jakarta.validation.*;
import org.jetbrains.annotations.NotNull;
import picocli.CommandLine;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@CommandLine.Command(name = "SalesTax",
        version = "SalesTax 1.0",
        mixinStandardHelpOptions = true,
        description = "Calculates sales tax for a given list of items or uses the default list.")
public class Application  implements Callable<Integer> {

    static Supplier<Application> applicationFactory = () -> new Application(System.out);
    static Consumer<Integer> applicationExitFactory = System::exit;


    @CommandLine.Parameters(index = "0",
            description = "Optional file path to use for baskets.",
           arity = "0")
    private String filepath;

    private PrintStream out;

    public Application(PrintStream out) {
        this.out = out;
    }

    @Override
    public Integer call() throws Exception {

        Supplier<List<String>> lineReader =
                Optional.ofNullable(filepath)
                        .map(this::fileBasketReader)
                        .orElseGet(this::defaultBasketReader);
        List<RawBasket> baskets = new BasketReader().read(lineReader);
        List<Set<ConstraintViolation<RawBasket>>> violations;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            violations = baskets.stream().map(basket -> validator.validate(basket)).toList();
        }

        if(aBasketIsInvalid(violations)) {
            provideFeedback(baskets, violations);
            return 2;
        }
        Mapper<RawBasket, Basket> basketMapper = new BasketMapper();
        OrderService orderService = new TaxHandlingOrderService();
        ReceiptWriter<String> receiptWriter = new StringReceiptWriter();
        List<String> receipts = baskets.stream()
                .map(basketMapper::map)
                .map(orderService::process)
                .map(receiptWriter::write)
                .toList();

        IntStream.range(0, receipts.size())
                .forEach(receiptIndex -> {

                    String receipt = receipts.get(receiptIndex);
                    out.printf("Output %d:%n", receiptIndex + 1);
                    out.println(receipt);
                });

        return 0;
    }

    private void provideFeedback(List<RawBasket> baskets, List<Set<ConstraintViolation<RawBasket>>> violations) {
        IntStream.range(0, violations.size())
                .forEach(basketIndex -> {
                    provideFeedbackForBasket(basketIndex+1,baskets.get(basketIndex), violations.get(basketIndex));
                });
    }

    private void provideFeedbackForBasket(int basketNumber, RawBasket rawBasket, Set<ConstraintViolation<RawBasket>> constraintViolations) {
        Comparator<ConstraintViolation<RawBasket>> path =
                Comparator.comparing((it) -> it.getPropertyPath().toString(), Comparator.naturalOrder());

        Comparator<ConstraintViolation<RawBasket>> message = Comparator.comparing(ConstraintViolation::getMessage, Comparator.naturalOrder());

        out.printf("Basket %d (%s) was invalid:%n", basketNumber, rawBasket.getName());
        constraintViolations.stream()
                .sorted(path.thenComparing(message))
                .forEach(violation -> out.println(violation.getMessage()));
    }

    private static boolean aBasketIsInvalid(List<Set<ConstraintViolation<RawBasket>>> violations) {
        return violations.stream().anyMatch(v -> !v.isEmpty());
    }

    private Supplier<List<String>> fileBasketReader(@NotNull String path) {
        final Path file = Path.of(path);
        return () -> new PathReader().read(file);
    }

    private Supplier<List<String>> defaultBasketReader() {
        return () -> new ClasspathResourceReader().read("example/baskets.dat");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(applicationFactory.get()).execute(args);
        applicationExitFactory.accept(exitCode);
    }
}
