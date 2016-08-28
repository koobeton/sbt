package ru.sbt.cacheproxy.proxy.persist;

import ru.sbt.cacheproxy.proxy.CacheOptions;

public interface PersistStrategy {

    boolean isCached(String key);

    void persist(Object result, CacheOptions options);

    Object restore(CacheOptions options);
}
