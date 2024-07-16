
# Factory

## TransactionParserFactory

The `TransactionParserFactory` creates the appropriate parser for a given file type.

### Methods

#### `createParser(String file)`

Creates a `TransactionParser` based on the file extension.

- **Parameters**: 
  - `file` (String): The name of the file.
  
- **Returns**: 
  - `TransactionParser`: The appropriate parser for the file type.
  
- **Throws**:
  - `TransactionsFileException`: If the file is null or has an invalid extension.
