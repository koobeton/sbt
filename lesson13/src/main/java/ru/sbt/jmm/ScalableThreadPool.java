package ru.sbt.jmm;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool implements ThreadPool {

    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final int minThreadsCount;
    private final int maxThreadsCount;
    private final AtomicInteger currentThreadsCount;

    public ScalableThreadPool(int minThreadsCount, int maxThreadsCount) {
        this.minThreadsCount = minThreadsCount;
        this.maxThreadsCount = maxThreadsCount;
        currentThreadsCount = new AtomicInteger();
    }

    @Override
    public void start() {
        while (currentThreadsCount.get() < minThreadsCount) {
            new Worker().start();
            currentThreadsCount.incrementAndGet();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            if (tasks.size() > currentThreadsCount.get()
                    && currentThreadsCount.get() < maxThreadsCount) {
                new Worker().start();
                currentThreadsCount.incrementAndGet();
            }
            //notify worker
            tasks.notify();
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable poll = null;
                synchronized (tasks) {
                    if (tasks.isEmpty()) {
                        if (currentThreadsCount.get() > minThreadsCount) {
                            this.interrupt();
                            currentThreadsCount.decrementAndGet();
                            return;
                        } else {
                            while (tasks.isEmpty()) {
                                try {
                                    tasks.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException("Interrupted exception: " + e.getMessage(), e);
                                }
                            }
                        }
                    } else {
                        poll = tasks.poll();
                    }
                }
                if (poll != null) poll.run(); // handle exception
            }
        }
    }
}
