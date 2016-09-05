package ru.sbt.concurrent.classwork;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class EqualityLockServiceTest {

    private Queue<Object> executionQueue;
    private Service s;

    @Before
    public void setUp() throws Exception {

        executionQueue = new ConcurrentLinkedQueue<>();

        s = new EqualityLockService((o) -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executionQueue.add(o);
        });
    }

    @Test
    public void run() throws Exception {

        new Thread(() -> s.run(1)).start();
        new Thread(() -> s.run(128)).start();
        new Thread(() -> s.run("fsdfs")).start();

        new Thread(() -> s.run(128)).start();
        new Thread(() -> s.run(128)).start();

        new Thread(() -> s.run(3)).start();

        Thread.sleep(2500);

        assertEquals(6, executionQueue.size());
        assertEquals(1, executionQueue.stream().skip(4).distinct().count());
    }
}