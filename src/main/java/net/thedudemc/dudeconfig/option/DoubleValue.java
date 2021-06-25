package net.thedudemc.dudeconfig.option;

public class DoubleValue extends Option<Double> {
    public DoubleValue(Double value) {
        super(value);
    }

    @Override
    public Option<Double> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public void set(Double value) {
        this.value = value;
    }
}
