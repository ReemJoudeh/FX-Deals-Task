# CsvTransactionParser

## Overview
The `CsvTransactionParser` class is responsible for parsing CSV files containing transaction data. It uses a `TransactionValidator` to ensure the integrity of the data.

## Methods

### parse(MultipartFile file)

- **Description**: Parses the given CSV file and returns a list of transactions. Throws `TransactionsFileException` if there are validation errors.
- **Parameters**:
    - `MultipartFile file`: The CSV file to parse.
- **Returns**: A list of `TransactionDTO` objects.
- **Throws**: `TransactionsFileException`

## Example

```java
CsvTransactionParser parser = new CsvTransactionParser(transactionValidator);
try {
    List<TransactionDTO> transactions = parser.parse(multipartFile);
} catch (TransactionsFileException e) {
    // Handle exception
}
