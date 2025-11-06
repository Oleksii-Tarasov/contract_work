package ua.gov.court.supreme.contractwork.exception;

public class DatabaseException extends BaseException {
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
