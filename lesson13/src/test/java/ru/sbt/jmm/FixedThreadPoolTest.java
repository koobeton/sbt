package ru.sbt.jmm;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class FixedThreadPoolTest {

    @Test
    public void taskTest() throws Exception {

        int nThreads = 3;

        ThreadPool pool = new FixedThreadPool(nThreads);

        Map<Integer, String> integersByThread = new ConcurrentHashMap<>();

        Consumer<Integer> task = (i) -> integersByThread.put(i, Thread.currentThread().getName());

        pool.execute(() -> task.accept(1));
        pool.execute(() -> task.accept(2));
        pool.execute(() -> task.accept(3));
        pool.execute(() -> task.accept(4));
        pool.execute(() -> task.accept(5));
        pool.execute(() -> task.accept(6));
        pool.execute(() -> task.accept(7));
        pool.execute(() -> task.accept(8));
        pool.execute(() -> task.accept(9));
        pool.execute(() -> task.accept(10));

        pool.start();

        pool.execute(() -> task.accept(11));
        pool.execute(() -> task.accept(12));
        pool.execute(() -> task.accept(13));
        pool.execute(() -> task.accept(14));
        pool.execute(() -> task.accept(15));
        pool.execute(() -> task.accept(16));
        pool.execute(() -> task.accept(17));
        pool.execute(() -> task.accept(18));
        pool.execute(() -> task.accept(19));
        pool.execute(() -> task.accept(20));

        Thread.sleep(1000);

        assertEquals(20, integersByThread.size());
        assertEquals(20, integersByThread.keySet().stream().distinct().count());
        assertEquals(nThreads, integersByThread.values().stream().distinct().count());
    }
}