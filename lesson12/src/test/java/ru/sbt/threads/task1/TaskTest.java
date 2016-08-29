package ru.sbt.threads.task1;

import org.junit.Test;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class TaskTest {

    private Task<String> task;
    private Callable<String> callable;

    @Test
    public void getResult() throws Exception {

        callable = () -> String.format("[first]: %s", Thread.currentThread().getName());
        task = new Task<>(callable);

        int nThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        Queue<String> results = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < nThreads + 2; i++) {
            results.add(executor.submit(task::get).get());
        }

        assertEquals(nThreads + 2, results.size());
        assertEquals(1, results.stream().distinct().count());
    }

    @Test
    public void getException() throws Exception {

        callable = () -> {
            throw new RuntimeException("[thrown from]: " + Thread.currentThread().getName());
        };
        task = new Task<>(callable);

        Map<Thread, Throwable> exceptionByThread = new ConcurrentHashMap<>();
        Thread.setDefaultUncaughtExceptionHandler(exceptionByThread::put);

        int nThreads = 5;
        for (int i = 0; i < nThreads; i++) {
            new Thread(task::get).start();
        }

        Thread.sleep(1000);

        assertEquals(nThreads, exceptionByThread.size());
        assertEquals(1, exceptionByThread.values().stream().distinct().count());
    }
}