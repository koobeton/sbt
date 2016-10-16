package ru.sbt.jdbc.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.jdbc.DBService;
import ru.sbt.jdbc.DBServiceImpl;
import ru.sbt.jdbc.dao.impl.StudentsDAOImpl;
import ru.sbt.jdbc.dataset.Student;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class StudentsDAOTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static final List<Student> TEST_STUDENTS = Arrays.asList(
            new Student("Вася", "Пупкин"),
            new Student("Иван", "Иванов"),
            new Student("Петр", "Петров"),
            new Student("Семен", "Семенов")
    );

    private DBService dbService;
    private StudentsDAO studentsDAO;

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceImpl(URL);
        dbService.createTables();
        studentsDAO = new StudentsDAOImpl(dbService.getConnection());
    }

    @After
    public void tearDown() throws Exception {
        dbService.shutdown();
    }

    @Test
    public void saveStudent() throws Exception {
        studentsDAO.saveStudent(TEST_STUDENTS.get(0));

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Students where name like 'Вася' and surname like 'Пупкин'");
            while (rs.next()) {
                assertEquals("Вася", rs.getString("name"));
                assertEquals("Пупкин", rs.getString("surname"));
            }
        }
    }

    @Test
    public void saveStudents() throws Exception {
        studentsDAO.saveStudents(TEST_STUDENTS);

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Students");
            int rows = 0;
            while (rs.next()) rows++;
            assertEquals(TEST_STUDENTS.size(), rows);
        }
    }

    @Test
    public void listStudents() throws Exception {
        studentsDAO.saveStudents(TEST_STUDENTS);

        List<Student> result = studentsDAO.listStudents();

        assertEquals(TEST_STUDENTS.size(), result.size());
    }

    @Test
    public void findStudentById() throws Exception {
        studentsDAO.saveStudents(TEST_STUDENTS);

        Student result = studentsDAO.findStudentById(1);

        assertEquals(1, result.getId());
        assertEquals("Вася", result.getName());
        assertEquals("Пупкин", result.getSurname());
    }

    @Test
    public void findStudentsByName() throws Exception {
        studentsDAO.saveStudents(TEST_STUDENTS);
        studentsDAO.saveStudent(new Student("Иван", "Второй"));

        List<Student> result = studentsDAO.findStudentsByName("Иван");

        assertEquals(2, result.size());
        result.forEach(s -> assertEquals("Иван", s.getName()));
    }

    @Test
    public void findStudentsBySurname() throws Exception {
        studentsDAO.saveStudents(TEST_STUDENTS);
        studentsDAO.saveStudent(new Student("Павел", "Иванов"));

        List<Student> result = studentsDAO.findStudentsBySurname("Иванов");

        assertEquals(2, result.size());
        result.forEach(s -> assertEquals("Иванов", s.getSurname()));
    }
}