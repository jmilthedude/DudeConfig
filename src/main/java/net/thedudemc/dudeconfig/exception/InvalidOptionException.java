package net.thedudemc.dudeconfig.exception;

import java.lang.reflect.Type;

public class InvalidOptionException extends RuntimeException {

    private final String key;
    private final String type;

    public InvalidOptionException(String key, String type) {
        this.key = key;
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Invalid type (attempted:" + type + ") or no value with the key: " + key;
    }

}
