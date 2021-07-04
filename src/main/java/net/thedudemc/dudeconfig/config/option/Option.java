package net.thedudemc.dudeconfig.config.option;

import com.google.gson.annotations.Expose;

public class Option<T> {

    @Expose protected T value;
    @Expose protected String comment;

    public Option(T value) {
        this.value = value;
    }

    public Option<T> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public static Option<?> of(Object value) {
        return new Option<>(value);
    }

    public T getValue() {
        return value;
    }

    public String getComment() {
        return this.comment;
    }


}
