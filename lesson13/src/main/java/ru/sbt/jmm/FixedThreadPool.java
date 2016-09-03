package ru.sbt.jmm;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {

    private final Queue<Runnable> tasks = new ArrayDeque<>();
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
        synchronized (tasks) {
            tasks.add(runnable);
            //notify worker
            tasks.notify();
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable poll;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("Interrupted exception: " + e.getMessage(), e);
                        }
                    }
                    poll = tasks.poll();
                }
                if (poll != null) poll.run(); // handle exception
            }
        }
    }
}