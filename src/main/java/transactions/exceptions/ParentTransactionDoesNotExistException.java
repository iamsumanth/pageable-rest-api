package transactions.exceptions;

public class ParentTransactionDoesNotExistException extends Exception {
    public ParentTransactionDoesNotExistException(String message) {
        super(message);
    }
}
