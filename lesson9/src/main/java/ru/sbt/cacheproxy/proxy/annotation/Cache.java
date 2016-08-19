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

    Type cacheType() default IN_MEMORY;

    int maxListSize() default UNLIMITED_LIST_SIZE;

    enum Type {
        IN_MEMORY,
        FILE
    }
}
