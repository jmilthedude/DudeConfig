package net.thedudemc.dudeconfig.option;

import com.google.gson.annotations.Expose;

public class RangedValue extends Option<Double> {

    @Expose
    private final Double min;
    @Expose
    private final Double max;

    public RangedValue(Double defaultValue, Double minValue, Double maxValue) {
        super(defaultValue);
        this.min = minValue;
        this.max = maxValue;

        withinRange(defaultValue);
    }

    public double clampValue(double value) {
        return this.clamp(value, this.min, this.max);
    }

    private double clamp(double num, double min, double max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }

    public boolean withinRange(double range) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be larger than maximum value!");
        } else if (range < min) {
            throw new IllegalArgumentException("Value cannot be lower than minimum value!");
        } else if (range > max) {
            throw new IllegalArgumentException("Value cannot be larger than maximum value!");
        }
        return true;
    }

    public void set(int range) {
        if (this.withinRange(range)) this.value = (double) range;
    }

    @Override
    public Option<Double> withComment(String comment) {
        this.comment = comment;
        return this;
    }
}
