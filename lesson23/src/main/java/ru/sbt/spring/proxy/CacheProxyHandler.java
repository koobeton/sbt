package ru.sbt.spring.proxy;

import ru.sbt.spring.proxy.annotation.Cache;
import ru.sbt.spring.proxy.persist.PersistStrategiesContext;
import ru.sbt.spring.proxy.persist.PersistStrategy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheProxyHandler implements InvocationHandler {

    private final Object delegate;
    private final String rootDir;
    private final PersistStrategiesContext persistContext;

    public CacheProxyHandler(Object delegate, String rootDir, PersistStrategiesContext context) {
        this.delegate = delegate;
        this.rootDir = rootDir;
        this.persistContext = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class)) return method.invoke(delegate, args);

        CacheOptions options = new CacheOptions(method, args, rootDir);

        PersistStrategy strategy = persistContext.getPersistStrategy(options.getCacheType());

        if (!strategy.isCached(options.key())) {
            Object result = method.invoke(delegate, args);
            result = options.checkReturnType(result);
            strategy.persist(result, options);
        }

        return strategy.restore(options);
    }
}
