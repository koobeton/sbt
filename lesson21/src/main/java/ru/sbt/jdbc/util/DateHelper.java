package ru.sbt.jdbc.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateHelper {

    public static long longDate(int year, int month, int dayOfMonth) {
        return Date.valueOf(LocalDate.of(year, month, dayOfMonth)).getTime();
    }
}
