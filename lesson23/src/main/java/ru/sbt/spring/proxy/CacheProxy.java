package ru.sbt.spring.proxy;

import ru.sbt.spring.proxy.persist.PersistStrategiesContext;

import java.lang.reflect.Proxy;

public class CacheProxy {

    private final String rootDir;
    private final PersistStrategiesContext persistContext;

    public CacheProxy(String rootDir, PersistStrategiesContext context) {
        this.rootDir = rootDir;
        this.persistContext = context;
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(Object delegate) {

        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(),
                new CacheProxyHandler(delegate, rootDir, persistContext));
    }
}
