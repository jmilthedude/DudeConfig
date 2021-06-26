package option;

public class IntValue extends Option<Integer> {
    public IntValue(Integer value) {
        super(value);
    }

    @Override
    public Option<Integer> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public void set(int value) {
        this.value = value;
    }
}
