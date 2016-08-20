package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.proxy.annotation.Cache;
import ru.sbt.cacheproxy.proxy.annotation.Ignore;
import ru.sbt.cacheproxy.utils.Utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

        Cache annotation = method.getAnnotation(Cache.class);

        Cache.Type cacheType = annotation.cacheType();
        int maxListSize = annotation.maxListSize();
        boolean zip = annotation.zip();
        String fileNameTemplate = rootDir + annotation.fileNamePrefix() + "%s" + (zip ? ".zip" : ".ser");

        String key = key(fileNameTemplate, method, args);
        if (!resultByArgs.containsKey(key)) {
            Object result = method.invoke(delegate, args);
            result = checkReturnType(result, method.getReturnType(), maxListSize);
            resultByArgs.put(key, result);
            if (cacheType == Cache.Type.FILE) persistResult((Serializable) result, key, zip);
        }

        return cacheType == Cache.Type.FILE
                ? restoreResult(key, zip)
                : resultByArgs.get(key);
    }

    private Object checkReturnType(final Object result, final Class<?> returnType, final int maxListSize) {
        return returnType == List.class
                && maxListSize != Cache.UNLIMITED_LIST_SIZE
                && maxListSize <= ((List<?>) result).size()
                ? ((List<?>) result).subList(0, maxListSize)
                : result;
    }

    private String key(final String fileNameTemplate, final Method method, final Object[] args) {
        return String.format(fileNameTemplate,
                method.getName()
                        + (args != null ? checkSignificantArgs(method, args) : ""));
    }

    private String checkSignificantArgs(final Method method, final Object[] args) {

        StringBuilder significantArgs = new StringBuilder();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (args.length != parameterAnnotations.length) {
            throw new IllegalStateException("Wrong args number in method: "
                    + method.getName());
        }
        for (int i = 0; i < args.length; i++) {
            if (Stream.of(parameterAnnotations[i])
                    .map(Annotation::annotationType)
                    .noneMatch(a -> a == Ignore.class)) {
                significantArgs.append(args[i]);
            }
        }

        return significantArgs.toString();
    }

    private void persistResult(final Serializable result, final String fileName, final boolean zip) {
        Utils.serialize(result, fileName, zip);
    }

    private Object restoreResult(final String fileName, final boolean zip) {
        return Utils.deserialize(fileName, zip);
    }
}
