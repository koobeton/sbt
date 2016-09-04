package ru.sbt.concurrent.classwork;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EqualityLockService implements Service {

    private final Service service;
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<Object, Condition> conditionByObject = new ConcurrentHashMap<>();

    public EqualityLockService(Service service) {
        this.service = service;
    }

    @Override
    public void run(final Object o) {

        Condition condition = conditionByObject.putIfAbsent(o, lock.newCondition());

        if (condition != null) {
            lock.lock();
            try {
                do {
                    condition.await();
                } while (!lock.isHeldByCurrentThread());
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted exception: " + e.getMessage(), e);
            } finally {
                lock.unlock();
            }
        }

        service.run(o);

        try {
            lock.lock();
            if (lock.hasWaiters(conditionByObject.get(o))) {
                conditionByObject.get(o).signal();
            } else {
                conditionByObject.remove(o);
            }
        } finally {
            lock.unlock();
        }
    }
}
