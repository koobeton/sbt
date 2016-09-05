package ru.sbt.concurrent.classwork;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class EqualityLockService implements Service {

    private final Service service;
    private final Map<Object, DoubleLock> lockByObject = new ConcurrentHashMap<>();

    public EqualityLockService(Service service) {
        this.service = service;
    }

    @Override
    public void run(final Object o) {

        DoubleLock doubleLock = lockByObject.putIfAbsent(o, new DoubleLock());
        if (doubleLock == null) doubleLock = lockByObject.get(o);

        doubleLock.outerLock().lock();
        try {
            doubleLock.innerLock().lock();
            service.run(o);
        } finally {
            doubleLock.outerLock().unlock();
            if (!doubleLock.outerLock().isLocked()) {
                lockByObject.remove(o);
            }
            doubleLock.innerLock().unlock();
        }
    }

    private final class DoubleLock {
        private final ReentrantLock outerLock = new ReentrantLock();
        private final ReentrantLock innerLock = new ReentrantLock();

        private ReentrantLock outerLock() {
            return outerLock;
        }

        private ReentrantLock innerLock() {
            return innerLock;
        }
    }
}
