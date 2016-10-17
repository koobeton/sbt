package ru.sbt.jdbc.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student(100500, "Вася", "Пупкин");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(100500, student.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Вася", student.getName());
    }

    @Test
    public void getSurname() throws Exception {
        assertEquals("Пупкин", student.getSurname());
    }

    @Test
    public void string() throws Exception {
        assertEquals("100500 : Пупкин Вася", student.toString());
    }
}