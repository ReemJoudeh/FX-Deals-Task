package fxDeals;

import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.model.Violation;
import fxDeals.parser.Impl.CsvTransactionParser;
import fxDeals.repository.DealRepository;
import fxDeals.repository.ViolationRepository;
import fxDeals.validator.TransactionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)

public class CsvTransactionParserTest {
    @InjectMocks
    private CsvTransactionParser csvTransactionParser;
    @Mock
    private TransactionValidator transactionValidator;
    @Mock
    private DealRepository dealRepository;
    @Mock
    private ViolationRepository violationRepository;

    @BeforeEach
    public void setUp() {
        csvTransactionParser = new CsvTransactionParser(transactionValidator, dealRepository,violationRepository);
    }

    @Test
    void givenNullFile_whenParse_thenShouldThrowException() {
        doThrow(new IllegalArgumentException("Transactions File cannot be null")).when(transactionValidator).validateFile(any());
        String message = Assertions.assertThrows(IllegalArgumentException.class,
                () -> csvTransactionParser.parse(null)).getMessage();
        Assertions.assertEquals("Transactions File cannot be null", message);

    }

    @Test
    void givenNotExistedFile_whenParse_thenShouldThrowException() throws IOException {
        doThrow(new IllegalArgumentException("Transactions File does not exist")).when(transactionValidator).validateFile(any());
        File file = new File("src/test/resources/CSVFiles/null-dealUniqueId.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);
        String message = Assertions.assertThrows(IllegalArgumentException.class,
                () -> csvTransactionParser.parse(multipartFile)).getMessage();

        Assertions.assertEquals("Transactions File does not exist", message);
    }

    @Test
    void givenInvalidFile_whenParse_thenShouldThrowException() throws IOException {
        doThrow(new TransactionsFileException("its not a csv file")).when(transactionValidator).validateFile(any());
        File file = new File("src/test/resources/CSVFiles/test.xml");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        String message = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getMessage();

        Assertions.assertEquals("its not a csv file", message);
    }

    @Test
    void givenNullDealUniqueId_whenParse_thenShouldThrowException() throws IOException {

        // Prepare the mock Violation
        Violation violation = new Violation("a null dealUniqueId field");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/null-dealUniqueId.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("a null dealUniqueId field", violations.get(1).get(0).getErrorMessage());

    }

    @Test
    void givenNullDealAmount_whenParse_thenShouldThrowException() throws IOException {

        // Prepare the mock Violation
        Violation violation = new Violation("a null deal amount Field");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/null-dealAmount.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("a null deal amount Field", violations.get(1).get(0).getErrorMessage());

    }

    @Test
    void givenInvalidDealAmount_whenParse_thenShouldThrowException() throws IOException {

        // Prepare the mock Violation
        Violation violation = new Violation("an invalid deal amount");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/invalid-dealAmount.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("an invalid deal amount", violations.get(1).get(0).getErrorMessage());

    }


    @Test
    void givenInvalidDealTimeStamp_whenParse_thenShouldThrowException() throws IOException {

        // Prepare the mock Violation
        Violation violation = new Violation("an invalid deal TimeStamp!");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/invalid-dealTimeStamp.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("an invalid deal TimeStamp!", violations.get(1).get(0).getErrorMessage());

    }

    @Test
    void givenNullDealTimeStamp_whenParse_thenShouldThrowException() throws IOException {

        // Prepare the mock Violation
        Violation violation = new Violation("a null deal TimeStamp Field");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/null-dealTimeStamp.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("a null deal TimeStamp Field", violations.get(1).get(0).getErrorMessage());

    }

    @Test
    public void givenNullFromCurrencyIsoCode_whenParse_thenShouldThrowException() throws IOException {
        // Prepare the mock Violation
        Violation violation = new Violation("a null fromCurrencyIsoCode field");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/null-fromCurrencyIsoCode.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("a null fromCurrencyIsoCode field", violations.get(1).get(0).getErrorMessage());
    }


    @Test
    public void givenInvalidFromCurrency_whenParse_thenShouldThrowException() throws IOException {
        // Prepare the mock Violation
        Violation violation = new Violation("an invalid fromCurrencyIsoCode");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/invalid-fromCurrencyIsoCode.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("an invalid fromCurrencyIsoCode", violations.get(1).get(0).getErrorMessage());
    }

    @Test
    public void givenInvalidToCurrency_whenParse_thenShouldThrowException() throws IOException {
        // Prepare the mock Violation
        Violation violation = new Violation("an invalid toCurrencyIsoCode!");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/invalid-toCurrencyIsoCode.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("an invalid toCurrencyIsoCode!", violations.get(1).get(0).getErrorMessage());
    }

    @Test
    public void givenNullToCurrency_whenParse_thenShouldThrowException() throws IOException {
        // Prepare the mock Violation
        Violation violation = new Violation("a null toCurrencyIsoCode field");
        List<Violation> violationList = new ArrayList<>();
        violationList.add(violation);

        // Mock the validateTransactionFile method to return the violationList
        Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

        // Prepare the mock MultipartFile
        File file = new File("src/test/resources/CSVFiles/null-toCurrencyIsoCode.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

        // Test the parse method and assert the exception and violations
        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("a null toCurrencyIsoCode field", violations.get(1).get(0).getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"MissingFromCurrencyIsoCode", "MissingdealUniqueId", "MissingToCurrencyIsoCode", "MissingAmountDeal"})
    public void givenMissingColumn_whenParse_thenShouldThrowException(String file) throws IOException {

        File testedFile = new File("src/test/resources/CSVFiles/" + file + ".csv");
        FileInputStream input = new FileInputStream(testedFile);
        MultipartFile multipartFile = new MockMultipartFile("file", testedFile.getName(), "text/csv", input);

        Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
                () -> csvTransactionParser.parse(multipartFile)).getViolations();
        Assertions.assertEquals("invalid fields count", violations.get(1).get(0).getErrorMessage());
    }

}



