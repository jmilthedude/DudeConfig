package net.thedudemc.dudeconfig.config.option;

import com.google.gson.annotations.Expose;

public class Option<T> {

    @Expose protected T value;
    @Expose protected T min;
    @Expose protected T max;
    @Expose protected String comment;

    public Option(T value) {
        this.value = value;
    }

    public Option<T> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Option<T> withRange(T min, T max) {
        if (!(this.value instanceof Number) || !(min instanceof Number) || !(max instanceof Number)) {
            throw new IllegalArgumentException("For an Option to be a range, it must be a number.");
        }

        this.min = (T) min;
        this.max = (T) max;
        validateRange();

        return this;
    }

    public void validateRange() {
        if (this.min == null || this.max == null) return;

        if (!(this.value instanceof Number) || !(this.min instanceof Number) || !(this.max instanceof Number)) {
            throw new IllegalArgumentException("For an Option to be a range, it must be a number.");
        }
        Number min = (Number) this.min;
        Number max = (Number) this.max;
        Number value = (Number) this.value;

        if (value.doubleValue() < min.doubleValue()) {
            this.value = this.min;
        } else if (value.doubleValue() > max.doubleValue()) {
            this.value = this.max;
        }
    }

    public static <V> Option<V> of(V value) {
        return new Option<>(value);
    }

    public T getValue() {
        return value;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public void setValue(T value) {
        this.value = value;
    }


    public String getComment() {
        return this.comment;
    }
}
