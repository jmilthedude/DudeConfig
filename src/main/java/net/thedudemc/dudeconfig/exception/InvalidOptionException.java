package net.thedudemc.dudeconfig.exception;

public class InvalidOptionException extends RuntimeException {

    private String key;
    private String expectedType;
    private String actualType;

    public InvalidOptionException(String expectedType, String actualType) {
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    public InvalidOptionException(String key) {
        this.key = key;
    }

    @Override
    public String getMessage() {
        if (expectedType == null || actualType == null) {
            return "No option found: " + key;
        }
        return "Invalid type: expected type=" + this.expectedType + " - actual type=" + this.actualType;
    }

}
