package ru.sbt.spring.proxy.persist;

import ru.sbt.spring.proxy.CacheOptions;

public interface PersistStrategy {

    boolean isCached(String key);

    void persist(Object result, CacheOptions options);

    Object restore(CacheOptions options);
}
