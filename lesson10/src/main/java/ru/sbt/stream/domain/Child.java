package ru.sbt.stream.domain;

public class Child extends Person {

    public Child() {
    }

    public Child(int age) {
        super(age);
    }

    public Child(String name) {
        super(name);
    }

    public Child(String name, int age) {
        super(name, age);
    }
}
