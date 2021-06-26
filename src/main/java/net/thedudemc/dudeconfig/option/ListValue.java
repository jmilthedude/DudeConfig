package net.thedudemc.dudeconfig.option;

import java.util.List;

public class ListValue<V> extends Option<List<V>> {
    public ListValue(List<V> value) {
        super(value);
    }

    @Override
    public Option<List<V>> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public boolean add(V value) {
        return this.value.add(value);
    }

    public boolean remove(V value) {
        return this.value.remove(value);
    }
}
