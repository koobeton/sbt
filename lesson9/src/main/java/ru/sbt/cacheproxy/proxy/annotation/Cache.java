package ru.sbt.cacheproxy.proxy.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static ru.sbt.cacheproxy.proxy.annotation.Cache.Type.IN_MEMORY;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Cache {
    int UNLIMITED_LIST_SIZE = -1;
    String DEFAULT_FILE_NAME_PREFIX = "";

    Type cacheType() default IN_MEMORY;

    int maxListSize() default UNLIMITED_LIST_SIZE;

    String fileNamePrefix() default DEFAULT_FILE_NAME_PREFIX;

    boolean zip() default false;

    enum Type {
        IN_MEMORY,
        FILE
    }
}
