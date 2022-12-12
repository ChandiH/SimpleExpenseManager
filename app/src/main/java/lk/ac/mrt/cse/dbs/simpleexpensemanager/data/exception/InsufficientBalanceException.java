package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception;

/**
 * This exception is thrown in case of an insufficient balance for transaction
 */
public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String detailMessage) {
        super(detailMessage);
    }

    public InsufficientBalanceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}