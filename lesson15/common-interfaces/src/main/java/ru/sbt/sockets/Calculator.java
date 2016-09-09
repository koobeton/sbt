package ru.sbt.sockets;

public interface Calculator {

    double calculate(Integer a, Integer b);

    void throwMeException();

    String getThreadName();
}
