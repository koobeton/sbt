package ru.sbt.jdbc.util;

public interface ThrowableConsumer<T, X extends Exception> {
    void accept(T t) throws X;
}
