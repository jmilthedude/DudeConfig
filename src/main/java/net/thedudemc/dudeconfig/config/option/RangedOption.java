package net.thedudemc.dudeconfig.config.option;

import com.google.gson.annotations.Expose;

public class RangedOption<T> extends Option<T> {

    @Expose private T min;
    @Expose private T max;

    public RangedOption(T value, T min, T max) {
        super(value);
        if (!(min instanceof Number) || !(max instanceof Number) || !(value instanceof Number))
            throw new IllegalArgumentException("Ranged value must be a number.");
        this.min = min;
        this.max = max;

        validate();
    }

    private void validate() {
        Number min = (Number) this.min;
        Number max = (Number) this.max;
        Number value = (Number) this.value;

        if (value.doubleValue() < min.doubleValue()) {
            this.value = this.min;
        } else if (value.doubleValue() > max.doubleValue()) {
            this.value = this.max;
        }
    }

    public static RangedOption<?> of(Object value, Object min, Object max) {
        return new RangedOption<>(value, min, max);
    }

}
