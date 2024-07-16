package fxDeals;

import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.factory.TransactionParserFactory;
import fxDeals.parser.TransactionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TransactionParserFactoryTest {
    private TransactionParserFactory transactionParserFactory;

    @BeforeEach
    public void setUp() {
        transactionParserFactory = new TransactionParserFactory();
    }

    @Test
    void givenNullFile_whenFactory_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFileException.class,
                () -> transactionParserFactory.createParser(null)).getMessage();
        Assertions.assertEquals("the file is null", message);
    }

    @Test
    void givenInvalidFile_whenFactory_thenShouldThrowException() {
        String message = Assertions.assertThrows(TransactionsFileException.class,
                () -> transactionParserFactory.createParser("anyfile.csvv")).getMessage();
        Assertions.assertEquals("Invalid Transaction File", message);
    }

    @Test
    void givenCSVFile_whenFactory_thenShouldCreateCSVParser() {
        String file = "src/test/resources/CSVFiles/ExtraColumn.csv";
        TransactionParser transactionParser = transactionParserFactory.createParser(file);
        Assertions.assertTrue(transactionParser instanceof TransactionParser);
    }


}

//Happy scenarios
