package ua.gov.court.supreme.contractwork.exception;

public class FileProcessingException extends BaseException {
    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
