package ru.sbt.cacheproxy.proxy.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Cache {
    int UNLIMITED_LIST_SIZE = -1;

    int maxListSize() default UNLIMITED_LIST_SIZE;
}
