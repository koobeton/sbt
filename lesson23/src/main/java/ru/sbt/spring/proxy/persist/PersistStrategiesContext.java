package ru.sbt.spring.proxy.persist;

import ru.sbt.spring.proxy.annotation.Cache;

import java.util.HashMap;
import java.util.Map;

public class PersistStrategiesContext {

    private final Map<Cache.Type, PersistStrategy> strategies = new HashMap<>();

    public void registerPersistStrategy(Cache.Type cacheType, PersistStrategy strategy) {
        strategies.put(cacheType, strategy);
    }

    public void removePersistStrategy(Cache.Type cacheType) {
        strategies.remove(cacheType);
    }

    public PersistStrategy getPersistStrategy(Cache.Type cacheType) {
        if (!strategies.containsKey(cacheType)) {
            throw new IllegalArgumentException(String.format(
                    "There is no persist strategy for cache type %s in this context.", cacheType));
        }
        return strategies.get(cacheType);
    }
}
