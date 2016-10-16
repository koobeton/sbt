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

import static org.junit.Assert.*;

public class StudentsDAOTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static final String NAME = "Вася";
    private static final String SURNAME = "Пупкин";

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
        studentsDAO.saveStudent(new Student(NAME, SURNAME));

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Students where name like 'Вася' and surname like 'Пупкин'");
            while (rs.next()) {
                assertEquals(NAME, rs.getString("name"));
                assertEquals(SURNAME, rs.getString("surname"));
            }
        }
    }
}