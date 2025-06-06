package bleshadow.dagger.internal;

import java.util.Collections;
import java.util.Map;
/* loaded from: classes.dex */
public final class MapBuilder<K, V> {
    private final Map<K, V> contributions;

    private MapBuilder(int size) {
        this.contributions = DaggerCollections.newLinkedHashMapWithExpectedSize(size);
    }

    public static <K, V> MapBuilder<K, V> newMapBuilder(int size) {
        return new MapBuilder<>(size);
    }

    public MapBuilder<K, V> put(K key, V value) {
        this.contributions.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        if (this.contributions.size() == 0) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(this.contributions);
    }
}
