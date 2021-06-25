package net.thedudemc.dudeconfig.option;

import java.util.Map;

public class MapValue<K, V> extends Option<Map<K, V>> {
    public MapValue(Map<K, V> value) {
        super(value);
    }

    @Override
    public Option<Map<K, V>> withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public V put(K key, V value) { return this.value.put(key, value); }
    public V remove(K key) { return this.value.remove(key); }
}
