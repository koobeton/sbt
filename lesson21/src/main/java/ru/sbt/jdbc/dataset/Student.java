package ru.sbt.jdbc.dataset;

public class Student {

    public static final long UNDEFINED_ID = -1;

    private final long id;
    private final String name;
    private final String surname;

    public Student(String name, String surname) {
        this(UNDEFINED_ID, name, surname);
    }

    public Student(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return String.format("%d : %s %s", id, surname, name);
    }
}
