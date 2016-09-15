package ru.sbt.bit.ood.solid.homework.utils;

import java.time.LocalDate;

public class DateRange {

    private final LocalDate from;
    private final LocalDate to;

    public DateRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate from() {
        return from;
    }

    public LocalDate to() {
        return to;
    }
}
