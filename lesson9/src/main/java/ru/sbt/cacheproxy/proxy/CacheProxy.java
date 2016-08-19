package ru.sbt.cacheproxy.proxy;

import java.lang.reflect.Proxy;

public class CacheProxy {

    private final String rootDir;

    public CacheProxy(String rootDir) {
        this.rootDir = rootDir;
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(Object delegate) {

        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(),
                new CacheProxyHandler(delegate));
    }
}
