package net.thedudemc.dudeconfig.config.option;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.exception.InvalidOptionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Option {

    @Expose protected Object value;
    @Expose protected Object min;
    @Expose protected Object max;
    @Expose protected String comment;

    public Option(Object value) {
        this.value = value;
    }

    public Option withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Option withRange(Object min, Object max) {
        if (!(this.value instanceof Number) || !(min instanceof Number) || !(max instanceof Number)) {
            throw new IllegalArgumentException("For an Option to be a range, it must be a number.");
        }

        this.min = min;
        this.max = max;
        validateRange();

        return this;
    }

    public void validateRange() {
        if (this.min == null || this.max == null) return;

        Number min = (Number) this.min;
        Number max = (Number) this.max;
        Number value = (Number) this.value;

        if (value.doubleValue() < min.doubleValue()) {
            this.value = this.min;
        } else if (value.doubleValue() > max.doubleValue()) {
            this.value = this.max;
        }
    }

    public static Option of(Object value) {
        return new Option(value);
    }

    public Object getValue() {
        return value;
    }

    public Object getMin() {
        return min;
    }

    public Object getMax() {
        return max;
    }

    public void setValue(Object value) {
        if (value.getClass() == this.value.getClass()) {
            this.value = value;
        } else {
            throw new InvalidOptionException(this.value.getClass().getSimpleName(), value.getClass().getSimpleName());
        }
    }

    public void setRawValue(Object value) {
        this.value = value;
    }

    public boolean getBooleanValue() {
        if (this.value.getClass().equals(Boolean.class)) {
            return (Boolean) this.value;
        }
        throw new InvalidOptionException(Boolean.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public int getIntValue() {
        if (Number.class.isAssignableFrom(this.value.getClass())) {
            return ((Number) this.value).intValue();
        }
        throw new InvalidOptionException(Integer.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public long getLongValue() {
        if (Number.class.isAssignableFrom(this.value.getClass())) {
            return ((Number) this.value).longValue();
        }
        throw new InvalidOptionException(Long.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public double getDoubleValue() {
        if (Number.class.isAssignableFrom(this.value.getClass())) {
            return ((Number) this.value).doubleValue();
        }
        throw new InvalidOptionException(Double.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public float getFloatValue() {
        if (Number.class.isAssignableFrom(this.value.getClass())) {
            return ((Number) this.value).floatValue();
        }
        throw new InvalidOptionException(Float.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public String getStringValue() {
        if (this.value instanceof String) {
            return (String) this.value;
        }
        throw new InvalidOptionException(String.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public HashMap<?, ?> getMapValue() {
        if (this.value instanceof Map) {
            return (HashMap<?, ?>) this.value;
        }
        throw new InvalidOptionException(HashMap.class.getSimpleName(), this.value.getClass().getSimpleName());
    }

    public List<?> getListValue() {
        if (this.value instanceof List) {
            return (List<?>) this.value;
        }
        throw new InvalidOptionException(List.class.getSimpleName(), this.value.getClass().getSimpleName());
    }


    public String getComment() {
        return this.comment;
    }

    @Override
    public String toString() {
        return min == null || max == null ?
                "Option{value=" + (value instanceof String ? "'" + value + "'" : value) + ", comment='" + comment + "'}" :
                "Option{value=" + (value instanceof String ? "'" + value + "'" : value) + ", min=" + min + ", max=" + max + ", comment='" + comment + "'}";
    }
}
