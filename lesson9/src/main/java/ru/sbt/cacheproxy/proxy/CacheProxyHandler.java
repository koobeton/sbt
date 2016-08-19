package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.proxy.annotation.Cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class CacheProxyHandler implements InvocationHandler {

    private final Map<Object, Object> resultByArgs = new HashMap<>();
    private final Object delegate;

    public CacheProxyHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class)) return method.invoke(delegate, args);

        Object key = key(method, args);
        if (!resultByArgs.containsKey(key)) {
            resultByArgs.put(key, method.invoke(delegate, args));
        }
        return resultByArgs.get(key);
    }

    private Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        if (args != null) key.addAll(Arrays.asList(args));
        return key;
    }
}
