package ru.sbt.cacheproxy.proxy;

import ru.sbt.cacheproxy.proxy.annotation.Cache;
import ru.sbt.cacheproxy.proxy.annotation.Ignore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CacheOptions {

    private final Method method;
    private final Object[] args;
    private final Cache.Type cacheType;
    private final int maxListSize;
    private final boolean zip;
    private final String key;

    CacheOptions(Method method, Object[] args, String rootDir) {
        this.method = method;
        this.args = args;

        if (!method.isAnnotationPresent(Cache.class)) {
            throw new IllegalArgumentException("Cache annotation is not found on the method "
                    + method.getName() + " where it is expected.");
        }

        Cache annotation = method.getAnnotation(Cache.class);
        this.cacheType = annotation.cacheType();
        this.maxListSize = annotation.maxListSize();
        this.zip = annotation.zip();
        this.key = generateKey(rootDir + annotation.fileNamePrefix() + "%s" + (zip ? ".zip" : ".ser"));
    }

    public Cache.Type getCacheType() {
        return cacheType;
    }

    public boolean isZip() {
        return zip;
    }

    public Object checkReturnType(final Object result) {
        return method.getReturnType() == List.class
                && maxListSize != Cache.UNLIMITED_LIST_SIZE
                && maxListSize <= ((List<?>) result).size()
                ? new ArrayList<>(((List<?>) result).subList(0, maxListSize))
                : result;
    }

    public String key() {
        return key;
    }

    private String generateKey(String fileNameTemplate) {
        return String.format(fileNameTemplate,
                method.getName()
                        + (args != null ? getSignificantArgs() : ""));
    }

    private String getSignificantArgs() {

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
}
