package ru.sbt.cacheproxy.proxy.persist;

import ru.sbt.cacheproxy.proxy.CacheOptions;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPersistStrategy implements PersistStrategy {

    private final Map<String, Object> resultByArgs = new HashMap<>();

    @Override
    public boolean isCached(String key) {
        return resultByArgs.containsKey(key);
    }

    @Override
    public void persist(Object result, CacheOptions options) {
        resultByArgs.put(options.key(), result);
    }

    @Override
    public Object restore(CacheOptions options) {
        return resultByArgs.get(options.key());
    }
}
