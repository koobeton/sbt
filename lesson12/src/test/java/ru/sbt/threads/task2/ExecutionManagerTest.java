package ru.sbt.threads.task2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExecutionManagerTest {

    private ExecutionManager manager;
    private Runnable callback;
    private Runnable runnable1000;
    private Runnable runnable5000;
    private Runnable runnableException;
    private boolean callbackExecuted;

    @Before
    public void setUp() throws Exception {
        manager = new ExecutionManagerImpl();
        callbackExecuted = false;
        callback = () -> callbackExecuted = true;
        runnable1000 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Runnable1000 was interrupted: " + e.getMessage(), e);
            }
        };
        runnable5000 = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Runnable5000 was interrupted: " + e.getMessage(), e);
            }
        };
        runnableException = () -> {
            throw new RuntimeException("Failed task");
        };
    }

    @Test
    public void execute() throws Exception {

        Context context = manager.execute(callback,
                runnable5000, runnable1000, runnableException, runnable1000,
                runnable5000, runnableException, runnable1000);

        assertEquals(0, context.getCompletedTaskCount());
        assertEquals(0, context.getFailedTaskCount());
        assertEquals(0, context.getInterruptedTaskCount());
        assertFalse(context.isFinished());
        assertFalse(callbackExecuted);

        Thread.sleep(3000);

        assertEquals(3, context.getCompletedTaskCount());
        assertEquals(2, context.getFailedTaskCount());
        assertEquals(0, context.getInterruptedTaskCount());
        assertFalse(context.isFinished());
        assertFalse(callbackExecuted);

        context.interrupt();

        Thread.sleep(50);

        assertEquals(3, context.getCompletedTaskCount());
        assertEquals(2, context.getFailedTaskCount());
        assertEquals(2, context.getInterruptedTaskCount());
        assertTrue(context.isFinished());
        assertTrue(callbackExecuted);
    }
}