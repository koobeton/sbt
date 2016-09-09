package ru.sbt.sockets;

public class CalculatorImpl implements Calculator {

    @Override
    public double calculate(Integer a, Integer b) {
        return a + b - 410 / 12;
    }

    @Override
    public void throwMeException() {
        throw new RuntimeException("Unable to calculate");
    }

    @Override
    public String getThreadName() {
        return Thread.currentThread().getName();
    }
}
