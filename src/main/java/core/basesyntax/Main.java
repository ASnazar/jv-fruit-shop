package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ParserInFruitTransaction;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserInFruitTransactionImpl;
import core.basesyntax.service.impl.ReadServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.TypeStrategy;
import core.basesyntax.strategy.impl.BalanceTypeStrategy;
import core.basesyntax.strategy.impl.FruitStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseStrategyImpl;
import core.basesyntax.strategy.impl.ReturnTypeStrategyImpl;
import core.basesyntax.strategy.impl.SupplyTypeStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_ALL_OPERATION = "src/main/resources/listAllOperation.csv";
    private static final String PATH_TO_REPORT = "src/main/resources/countProduct.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, TypeStrategy> actionsMap = new HashMap<>();
        actionsMap.put(FruitTransaction.Operation.BALANCE, new BalanceTypeStrategy());
        actionsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyTypeStrategyImpl());
        actionsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseStrategyImpl());
        actionsMap.put(FruitTransaction.Operation.RETURN, new ReturnTypeStrategyImpl());

        ReadService readService = new ReadServiceImpl();
        ParserInFruitTransaction parser = new ParserInFruitTransactionImpl();
        FruitStrategy fruitStrategy = new FruitStrategyImpl(actionsMap);
        FruitService fruitService = new FruitServiceImpl();
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();

        List<String> strings = readService.readFromFile(PATH_TO_ALL_OPERATION);
        List<FruitTransaction> fruitTransactions = parser.parseData(strings);

        fruitService.getAllTypeStrategy(fruitTransactions, fruitStrategy);
        writerService.write(PATH_TO_REPORT,reportService.reportStorage());

    }
}
