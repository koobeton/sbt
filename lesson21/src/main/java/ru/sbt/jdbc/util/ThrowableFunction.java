package ru.sbt.jdbc.util;

public interface ThrowableFunction<T, R, X extends Exception> {
    R apply(T t) throws X;
}
