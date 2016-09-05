package ru.sbt.threads.task2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContextImpl implements Context {

    private final ThreadPool pool;
    private final int totalTaskCount;
    private final AtomicInteger completed;
    private final AtomicInteger failed;
    private final AtomicInteger interrupted;
    private final Lock lock;

    public ContextImpl(ThreadPool pool, int totalTaskCount) {
        this.pool = pool;
        this.totalTaskCount = totalTaskCount;
        completed = new AtomicInteger();
        failed = new AtomicInteger();
        interrupted = new AtomicInteger();
        lock = new ReentrantLock();
    }

    @Override
    public int getCompletedTaskCount() {
        return completed.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failed.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interrupted.get();
    }

    @Override
    public void interrupt() {
        pool.interrupt();
    }

    @Override
    public boolean isFinished() {
        lock.lock();
        try {
            return completed.get() + failed.get() + interrupted.get() == totalTaskCount;
        } finally {
            lock.unlock();
        }
    }

    public void incrementCompletedTaskCount() {
        completed.incrementAndGet();
    }

    public void incrementFailedTaskCount() {
        failed.incrementAndGet();
    }

    public void incrementInterruptedTaskCount() {
        interrupted.incrementAndGet();
    }
}
