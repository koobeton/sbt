package ru.sbt.threads.task2;

public class ExecutionManagerImpl implements ExecutionManager {

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        ThreadPool pool = new ThreadPool();
        Context context = new ContextImpl(pool, tasks.length);
        new Thread(() -> pool.runThreadPool(context, callback, tasks)).start();
        return context;
    }
}
