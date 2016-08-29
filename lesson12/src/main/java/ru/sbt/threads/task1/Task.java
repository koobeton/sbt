package ru.sbt.threads.task1;

import java.util.concurrent.Callable;

public class Task<T> {

    private final Callable<? extends T> callable;
    private final Object lock = new Object();
    private volatile TaskException exception;
    private volatile T result;
    private volatile boolean calculated = false;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {

        if (calculated) return getResult();

        synchronized (lock) {
            if (calculated) {
                return getResult();
            } else {
                calculate();
                return getResult();
            }
        }
    }

    private void calculate() {
        try {
            result = callable.call();
        } catch (Exception e) {
            exception = new TaskException("Task exception: " + e.getMessage(), e);
        } finally {
            calculated = true;
        }
    }

    private T getResult() {
        if (exception != null) throw exception;
        else return result;
    }
}
