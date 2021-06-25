package net.thedudemc.dudeconfig.option;

public class StringValue extends Option<String> {
    public StringValue(String value) {
        super(value);
    }

    @Override
    public Option<String> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public void set(String value) {
        this.value = value;
    }
}
