package ru.sbt.jdbc.dataset;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class LessonTest {

    private Lesson lesson;
    private long date;

    @Before
    public void setUp() throws Exception {
        date = Date.valueOf(LocalDate.of(2016, 10, 14)).getTime();
        lesson = new Lesson(100500, "Java memory model", date);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(100500, lesson.getId());
    }

    @Test
    public void getSubject() throws Exception {
        assertEquals("Java memory model", lesson.getSubject());
    }

    @Test
    public void getDate() throws Exception {
        assertEquals(date, lesson.getDate());
    }

    @Test
    public void string() throws Exception {
        assertEquals("100500 : Java memory model @ 14.10.2016", lesson.toString());
    }
}