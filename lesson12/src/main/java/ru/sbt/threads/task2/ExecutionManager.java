package ru.sbt.threads.task2;

public interface ExecutionManager {

    Context execute(Runnable callback, Runnable... tasks);
}
