package lesson4;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<K> implements CountMap<K> {

    private final Map<K, Integer> map = new HashMap<>();

    @Override
    public void add(K o) {
        add(o, 1);
    }

    private void add(K o, int increment) {
        map.put(o, getCount(o) + increment);
    }

    @Override
    public int getCount(K o) {
        Integer v = map.get(o);
        return v == null ? 0 : v;
    }

    @Override
    public int remove(K o) {
        int v = getCount(o);
        if (v == 1) {
            map.remove(o);
        } else {
            map.replace(o, v - 1);
        }
        return v;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<? extends K> source) {
        for (Map.Entry<? extends K, Integer> sourceEntry : source.toMap().entrySet()) {
            this.add(sourceEntry.getKey(), sourceEntry.getValue());
        }
    }

    @Override
    public Map<K, Integer> toMap() {
        Map<K, Integer> clone = new HashMap<>();
        toMap(clone);
        return clone;
    }

    @Override
    public void toMap(Map<? super K, Integer> destination) {
        destination.putAll(this.map);
    }
}
