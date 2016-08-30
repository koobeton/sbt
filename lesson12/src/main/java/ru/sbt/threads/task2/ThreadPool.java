package ru.sbt.threads.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ThreadPool {

    private final List<Thread> pool = new ArrayList<>();

    public void runThreadPool(Context context, Runnable callback, Runnable... tasks) {

        startTasks(context, tasks);

        joinThreads();

        runCallback(callback);
    }

    public void interrupt() {
        pool.forEach(Thread::interrupt);
    }

    private void startTasks(Context context, Runnable... tasks) {
        Consumer<Runnable> countingRunnable = getCountingRunnable(context);
        for (Runnable task : tasks) {
            Thread thread = new Thread(() -> countingRunnable.accept(task));
            pool.add(thread);
            thread.start();
        }
    }

    private void joinThreads() {
        for (Thread thread : pool) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread pool was interrupted: " + e.getMessage(), e);
            }
        }
    }

    private void runCallback(Runnable callback) {
        Thread callbackThread = new Thread(callback);
        callbackThread.start();
        try {
            callbackThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread pool was interrupted: " + e.getMessage(), e);
        }
    }

    private Consumer<Runnable> getCountingRunnable(Context context) {
        return (r) -> {
            try {
                r.run();
                ((ContextImpl) context).incrementCompletedTaskCount();
            } catch (Exception e) {
                if (e.getClass() == InterruptedException.class
                        || (e.getCause() != null && e.getCause().getClass() == InterruptedException.class)) {
                    ((ContextImpl) context).incrementInterruptedTaskCount();
                } else {
                    ((ContextImpl) context).incrementFailedTaskCount();
                }
            }
        };
    }
}
