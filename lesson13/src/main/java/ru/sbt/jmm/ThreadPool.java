package ru.sbt.jmm;

public interface ThreadPool {

    void start();

    void execute(Runnable runnable);
}
