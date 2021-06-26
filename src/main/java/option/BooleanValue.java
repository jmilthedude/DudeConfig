package option;

public class BooleanValue extends Option<Boolean> {
    public BooleanValue(Boolean value) {
        super(value);
    }

    @Override
    public Option<Boolean> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public void set(boolean value) {
        this.value = value;
    }
}
