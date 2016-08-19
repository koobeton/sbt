package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.proxy.annotation.Cache;
import ru.sbt.cacheproxy.proxy.annotation.Ignore;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CacheProxyHandler implements InvocationHandler {

    private final Map<Object, Object> resultByArgs = new HashMap<>();
    private final Object delegate;

    public CacheProxyHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!method.isAnnotationPresent(Cache.class)) return method.invoke(delegate, args);

        Cache annotation = method.getAnnotation(Cache.class);

        Cache.Type cacheType = annotation.cacheType();
        int maxListSize = annotation.maxListSize();

        Object key = key(method, args);
        if (!resultByArgs.containsKey(key)) {
            Object result = method.invoke(delegate, args);
            if (method.getReturnType() == List.class
                    && maxListSize != Cache.UNLIMITED_LIST_SIZE
                    && maxListSize <= ((List<?>) result).size()) {
                result = ((List<?>) result).subList(0, maxListSize);
            }
            resultByArgs.put(key, result);
        }
        return resultByArgs.get(key);
    }

    private Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        if (args != null) {
            List<Object> significantArgs = new ArrayList<>();

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (args.length != parameterAnnotations.length) {
                throw new IllegalStateException("Wrong number of args");
            }
            for (int i = 0; i < args.length; i++) {
                List<Class<? extends Annotation>> annotations = Arrays.stream(parameterAnnotations[i])
                        .map(Annotation::annotationType)
                        .collect(Collectors.toList());
                if (!annotations.contains(Ignore.class)) {
                    significantArgs.add(args[i]);
                }
            }

            key.addAll(significantArgs);
        }
        return key;
    }
}
