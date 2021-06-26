package net.thedudemc.dudeconfig.option;

import com.google.gson.annotations.Expose;

public abstract class Option<T> {
    @Expose protected T value;
    @Expose protected String comment;

    public Option(T value) {
        this.value = value;
    }

    public abstract Option<T> withComment(String comment);

    public T getValue() {
        return value;
    }

    public String getComment() {
        return this.comment;
    }


}
