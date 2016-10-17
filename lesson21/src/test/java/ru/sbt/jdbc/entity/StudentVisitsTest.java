package ru.sbt.jdbc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentVisitsTest {

    private StudentVisits visits;

    @Before
    public void setUp() throws Exception {
        visits = new StudentVisits(100, 500);
    }

    @Test
    public void getStudentId() throws Exception {
        assertEquals(100, visits.getStudentId());
    }

    @Test
    public void getLessonId() throws Exception {
        assertEquals(500, visits.getLessonId());
    }

    @Test
    public void string() throws Exception {
        assertEquals("100 : 500", visits.toString());
    }
}