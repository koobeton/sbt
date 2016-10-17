package ru.sbt.jdbc.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private final long id;
    private final String subject;
    private final long date;

    public Lesson(long id, String subject, long date) {
        this.id = id;
        this.subject = subject;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%d : %s @ %s", id, subject, DATE_FORMAT.format(new Date(date)));
    }
}
