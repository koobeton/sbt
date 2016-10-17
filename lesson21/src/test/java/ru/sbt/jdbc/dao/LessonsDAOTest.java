package ru.sbt.jdbc.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sbt.jdbc.DBService;
import ru.sbt.jdbc.DBServiceImpl;
import ru.sbt.jdbc.dao.impl.LessonDAOImpl;
import ru.sbt.jdbc.entity.Lesson;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.sbt.jdbc.util.DateHelper.longDate;

public class LessonsDAOTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static final List<Lesson> TEST_LESSONS = Arrays.asList(
            new Lesson("Java", longDate(2016, 10, 14)),
            new Lesson("C++", longDate(2016, 10, 15)),
            new Lesson("JavaScript", longDate(2016, 10, 16)),
            new Lesson("Basic", longDate(2016, 10, 17)),
            new Lesson("CSS", longDate(2016, 10, 18))
    );

    private DBService dbService;
    private LessonsDAO lessonsDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceImpl(URL);
        dbService.createTables();
        lessonsDAO = new LessonDAOImpl(dbService.getConnection());
    }

    @After
    public void tearDown() throws Exception {
        dbService.shutdown();
    }

    @Test
    public void saveLesson() throws Exception {
        lessonsDAO.saveLesson(TEST_LESSONS.get(0));

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Lessons where subject = 'Java' and date = " + TEST_LESSONS.get(0).getDate());
            while (rs.next()) {
                assertEquals("Java", rs.getString("subject"));
                assertEquals(TEST_LESSONS.get(0).getDate(), rs.getLong("date"));
            }
        }
    }

    @Test
    public void saveLessons() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Lessons");
            int rows = 0;
            while (rs.next()) rows++;
            assertEquals(TEST_LESSONS.size(), rows);
        }
    }

    @Test
    public void listLessons() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);

        List<Lesson> result = lessonsDAO.listLessons();

        assertEquals(TEST_LESSONS.size(), result.size());
    }

    @Test
    public void findLessonById() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);

        Lesson result = lessonsDAO.findLessonById(1);

        assertEquals(1, result.getId());
        assertEquals("Java", result.getSubject());
        assertEquals(TEST_LESSONS.get(0).getDate(), result.getDate());
    }

    @Test
    public void findLessonsBySubject() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);
        lessonsDAO.saveLesson(new Lesson("Java", longDate(2016, 10, 8)));

        List<Lesson> result = lessonsDAO.findLessonsBySubject("Java");

        assertEquals(2, result.size());
        result.forEach(s -> assertEquals("Java", s.getSubject()));
    }

    @Test
    public void findLessonsByDate() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);
        lessonsDAO.saveLesson(new Lesson("Java", longDate(2016, 10, 16)));

        List<Lesson> result = lessonsDAO.findLessonsByDate(longDate(2016, 10, 16));

        assertEquals(2, result.size());
        result.forEach(s -> assertEquals(longDate(2016, 10, 16), s.getDate()));
    }

    @Test
    public void findLessonsBySubjectAndDate() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);

        List<Lesson> result = lessonsDAO.findLessonsBySubjectAndDate("Java", longDate(2016, 10, 14));

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getSubject());
        assertEquals(longDate(2016, 10, 14), result.get(0).getDate());
    }

    @Test
    public void updateLesson() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);
        long id = 1;
        Lesson before = lessonsDAO.findLessonById(id);

        lessonsDAO.updateLesson(new Lesson(id, "Fortran", longDate(2016, 9, 1)));
        Lesson after = lessonsDAO.findLessonById(id);

        assertEquals(before.getId(), after.getId());
        assertNotEquals(before.getSubject(), after.getSubject());
        assertNotEquals(before.getDate(), after.getDate());
        assertEquals("Fortran", after.getSubject());
        assertEquals(longDate(2016, 9, 1), after.getDate());
    }

    @Test
    public void updateLessonWithUndefinedId() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unable to update lesson with undefined id, try to save it first");

        lessonsDAO.updateLesson(new Lesson("Kotlin", longDate(2016, 1, 1)));
    }

    @Test
    public void deleteLesson() throws Exception {
        lessonsDAO.saveLessons(TEST_LESSONS);
        long id = 1;

        lessonsDAO.deleteLesson(id);

        assertEquals(TEST_LESSONS.size() - 1, lessonsDAO.listLessons().size());
        assertNull(lessonsDAO.findLessonById(id));
    }
}