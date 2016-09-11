package ru.sbt.sockets;

public interface Calculator {

    double calculate(int a, int b);

    void throwMeException();

    String getThreadName();
}
