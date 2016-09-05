package ru.sbt.jmm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final int threadCount;

    public FixedThreadPool(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void start() {
        for (int i = 0; i < threadCount; i++) {
            new Worker().start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted exception: " + e.getMessage(), e);
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    tasks.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted exception: " + e.getMessage(), e);
                }
            }
        }
    }
}