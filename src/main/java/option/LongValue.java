package option;

public class LongValue extends Option<Long> {
    public LongValue(Long value) {
        super(value);
    }

    @Override
    public Option<Long> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public void set(long value) {
        this.value = value;
    }
}
