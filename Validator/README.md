# Validator Module

## Overview
The validator module is responsible for validating various aspects of a transaction.

## Classes

### DefaultTransactionValidator
An abstract class that provides default implementations for transaction validation.

#### Methods

- `validateTransactionFile(TransactionDTO dto)`: Validates the transaction file and returns a list of violations.
- `validateDealUniqueId(String dealUniqueId, List<Violation> violations)`: Validates the deal unique ID.
- `validateFromCurrencyIsoCode(String fromCurrencyIsoCode, List<Violation> violations)`: Validates the from currency ISO code.
- `validateToCurrencyIsoCode(String toCurrencyIsoCode, List<Violation> violations)`: Validates the to currency ISO code.
- `validateDealAmount(String dealAmount, List<Violation> violations)`: Validates the deal amount.
- `validateDealTimestamp(String dealTimestamp, List<Violation> violations)`: Validates the deal timestamp.
