package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.proxy.annotation.Cache;
import ru.sbt.cacheproxy.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CacheProxyHandler implements InvocationHandler {

    private final Map<String, Object> resultByArgs = new HashMap<>();
    private final Object delegate;
    private final String rootDir;

    public CacheProxyHandler(Object delegate, String rootDir) {
        this.delegate = delegate;
        this.rootDir = rootDir;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class)) return method.invoke(delegate, args);

        CacheOptions options = new CacheOptions(method, args, rootDir);

        String key = options.key();
        if (!resultByArgs.containsKey(key)) {
            Object result = method.invoke(delegate, args);
            result = options.checkReturnType(result);
            resultByArgs.put(key, result);
            if (options.getCacheType() == Cache.Type.FILE) {
                Utils.serialize((Serializable) result, key, options.isZip());
            }
        }

        return options.getCacheType() == Cache.Type.FILE
                ? Utils.deserialize(key, options.isZip())
                : resultByArgs.get(key);
    }
}
