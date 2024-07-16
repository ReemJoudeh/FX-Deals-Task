package fxDeals.Exceptions;

public class EmptyTransactionFileException extends RuntimeException {
    public EmptyTransactionFileException(String message) {
        super(message);
    }
}