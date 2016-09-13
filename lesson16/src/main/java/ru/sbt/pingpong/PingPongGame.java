package ru.sbt.pingpong;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongGame {

    private static final Lock OUTER_LOCK = new ReentrantLock();
    private static final Lock INNER_LOCK = new ReentrantLock();

    public static void main(String... args) {

        PingPongGame game = new PingPongGame();

        new Thread(game::startGame, "Ping").start();
        new Thread(game::startGame, "Pong").start();
    }

    private void startGame() {
        while (true) {
            try {
                OUTER_LOCK.lock();
                INNER_LOCK.lock();
            } finally {
                OUTER_LOCK.unlock();
                makeTurn();
                try {
                    INNER_LOCK.unlock();
                } catch (IllegalMonitorStateException ignore) {
                }
            }
        }
    }

    private void makeTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Turn of " + Thread.currentThread().getName() +
                    " was interrupted: " + e.getMessage(), e);
        }
        System.out.println(Thread.currentThread().getName());
    }
}
