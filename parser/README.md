# Parser Module

## Overview
The parser module is responsible for parsing transaction files, particularly in CSV format.

## Classes

### CsvTransactionParser
Parses CSV files containing transaction data.

#### Methods

- `parse(MultipartFile file)`: Parses the given CSV file and returns a list of transactions. Throws `TransactionsFileException` if there are validation errors.
