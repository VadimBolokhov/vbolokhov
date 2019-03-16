package ru.job4j;

/**
 * To be thrown if command line args are specified incorrectly.
 * @author Vadim Bolokhov
 */
public class ArgsException extends Exception {
    /** Error code */
    private final ErrorCode errorCode;

    public ArgsException(ErrorCode code) {
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorCode.getMessage();
    }

    /**
     * Error code enumeration. Contains error messages.
     */
    public enum ErrorCode {
        SEARCH_DIR_MISSING("Search directory is not specified"),
        DESTINATION_MISSING("Destination file is not specified."),
        PATTERN_MISSING("Search pattern is not specified.");

        private String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
