package ru.sbt.threads.task1;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {

    private final Callable<? extends T> callable;
    private final Lock lock = new ReentrantLock();
    private TaskException exception;
    private T result;
    private AtomicBoolean calculated = new AtomicBoolean();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {

        if (calculated.get()) return getResult();

        lock.lock();
        try {
            if (!calculated.get()) {
                calculate();
            }
            return getResult();
        } finally {
            lock.unlock();
        }
    }

    private void calculate() {
        try {
            result = callable.call();
        } catch (Exception e) {
            exception = new TaskException("Task exception: " + e.getMessage(), e);
        } finally {
            calculated.set(true);
        }
    }

    private T getResult() {
        if (exception != null) throw exception;
        else return result;
    }
}
