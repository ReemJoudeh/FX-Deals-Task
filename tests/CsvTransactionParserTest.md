# CsvTransactionParserTest

## Overview
This test class verifies the behavior of the `CsvTransactionParser` class.

## Test Methods

### givenInvalidCurrency_whenParse_thenShouldThrowException

- **Description**: Verifies that when a CSV file with an invalid currency is parsed, a `TransactionsFileException` is thrown, and the appropriate violation message is included in the exception.
- **Assertions**:
    - A `TransactionsFileException` is thrown.
    - The violation message is "an invalid Currency!".

## Example

```java
@Test
public void givenInvalidCurrency_whenParse_thenShouldThrowException() throws IOException {
    // Prepare the mock Violation
    Violation violation = new Violation("an invalid Currency!");
    List<Violation> violationList = new ArrayList<>();
    violationList.add(violation);

    // Mock the validateTransactionFile method to return the violationList
    Mockito.lenient().when(transactionValidator.validateTransactionFile(any())).thenReturn(violationList);

    // Prepare the mock MultipartFile
    File file = new File("src/test/resources/CSVFiles/invalidCurrency.csv");
    FileInputStream input = new FileInputStream(file);
    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", input);

    // Test the parse method and assert the exception and violations
    Map<Integer, List<Violation>> violations = Assertions.assertThrows(TransactionsFileException.class,
            () -> csvTransactionParser.parse(multipartFile)).getViolations();
    Assertions.assertEquals("an invalid Currency!", violations.get(0).get(0).getErrorMessage());
}
