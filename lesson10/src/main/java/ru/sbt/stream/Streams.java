package ru.sbt.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {

    private Collection<T> collection;

    private Streams() {
    }

    public static <T> Streams<T> of(Collection<T> collection) {
        Streams<T> newInstance = new Streams<>();
        newInstance.collection = new ArrayList<>(collection);
        return newInstance;
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        Collection<T> result = new ArrayList<>();
        for (T e : collection) {
            if (predicate.test(e)) {
                result.add(e);
            }
        }
        collection = result;
        return this;
    }

    public Streams<T> transform(Function<? super T, ? extends T> transformer) {
        Collection<T> result = new ArrayList<>();
        for (T e : collection) {
            result.add(transformer.apply(e));
        }
        collection = result;
        return this;
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key,
                                  Function<? super T, ? extends V> value) {
        Map<K, V> result = new HashMap<>();
        for (T e : collection) {
            result.put(key.apply(e), value.apply(e));
        }
        return result;
    }
}

