package ru.sbt.jdbc.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.sbt.jdbc.DBService;
import ru.sbt.jdbc.DBServiceImpl;
import ru.sbt.jdbc.dao.impl.LessonDAOImpl;
import ru.sbt.jdbc.dao.impl.StudentVisitsDAOImpl;
import ru.sbt.jdbc.dao.impl.StudentsDAOImpl;
import ru.sbt.jdbc.entity.Lesson;
import ru.sbt.jdbc.entity.Student;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.sbt.jdbc.util.DateHelper.longDate;

public class StudentVisitsDAOTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static final List<Lesson> TEST_LESSONS = Arrays.asList(
            new Lesson("Java", longDate(2016, 10, 14)),
            new Lesson("C++", longDate(2016, 10, 15)),
            new Lesson("JavaScript", longDate(2016, 10, 16)),
            new Lesson("Basic", longDate(2016, 10, 17)),
            new Lesson("CSS", longDate(2016, 10, 18))
    );
    private static final List<Student> TEST_STUDENTS = Arrays.asList(
            new Student("Вася", "Пупкин"),
            new Student("Иван", "Иванов"),
            new Student("Петр", "Петров"),
            new Student("Семен", "Семенов")
    );

    private DBService dbService;
    private StudentsDAO studentsDAO;
    private LessonsDAO lessonsDAO;
    private StudentVisitsDAO studentVisitsDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceImpl(URL);
        dbService.createTables();
        studentsDAO = new StudentsDAOImpl(dbService.getConnection());
        lessonsDAO = new LessonDAOImpl(dbService.getConnection());
        studentVisitsDAO = new StudentVisitsDAOImpl(dbService.getConnection());

        studentsDAO.saveStudents(TEST_STUDENTS);
        lessonsDAO.saveLessons(TEST_LESSONS);
    }

    @After
    public void tearDown() throws Exception {
        dbService.shutdown();
    }

    @Test
    public void registerStudentAtLesson() throws Exception {
        int id = 1;
        studentVisitsDAO.registerStudentAtLesson(studentsDAO.findStudentById(id), lessonsDAO.findLessonById(id));

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Student_visits where student_id = 1 and lesson_id = 1");
            while (rs.next()) {
                assertEquals(id, rs.getLong("student_id"));
                assertEquals(id, rs.getLong("lesson_id"));
            }
        }
    }

    @Test
    public void removeStudentFromLesson() throws Exception {
        Student vasya = studentsDAO.findStudentById(1);
        Student ivan = studentsDAO.findStudentById(2);
        Lesson java = lessonsDAO.findLessonById(1);
        Lesson cpp = lessonsDAO.findLessonById(2);
        Lesson javascript = lessonsDAO.findLessonById(3);

        studentVisitsDAO.registerStudentAtLesson(vasya, java);
        studentVisitsDAO.registerStudentAtLesson(vasya, cpp);
        studentVisitsDAO.registerStudentAtLesson(vasya, javascript);
        studentVisitsDAO.registerStudentAtLesson(ivan, java);
        studentVisitsDAO.registerStudentAtLesson(ivan, cpp);
        studentVisitsDAO.registerStudentAtLesson(ivan, javascript);

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Student_visits");
            int rows = 0;
            while (rs.next()) rows++;
            assertEquals(6, rows);
        }

        studentVisitsDAO.removeStudentFromLesson(vasya, javascript);

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Student_visits");
            int rows = 0;
            while (rs.next()) rows++;
            assertEquals(5, rows);

            rs = stmt.executeQuery("select * from Student_visits where student_id = 1 and lesson_id = 3");
            assertFalse(rs.next());
        }
    }

    @Test
    public void findStudentsByLesson() throws Exception {
        Student vasya = studentsDAO.findStudentById(1);
        Student ivan = studentsDAO.findStudentById(2);
        Student peter = studentsDAO.findStudentById(3);
        Lesson java = lessonsDAO.findLessonById(1);
        Lesson cpp = lessonsDAO.findLessonById(2);
        Lesson javascript = lessonsDAO.findLessonById(3);

        studentVisitsDAO.registerStudentAtLesson(vasya, java);
        studentVisitsDAO.registerStudentAtLesson(vasya, cpp);
        studentVisitsDAO.registerStudentAtLesson(vasya, javascript);
        studentVisitsDAO.registerStudentAtLesson(ivan, java);
        studentVisitsDAO.registerStudentAtLesson(ivan, cpp);
        studentVisitsDAO.registerStudentAtLesson(ivan, javascript);
        studentVisitsDAO.registerStudentAtLesson(peter, javascript);

        List<Student> studentsAtJava = studentVisitsDAO.findStudentsByLesson(java);

        assertEquals(2, studentsAtJava.size());
    }

    @Test
    public void findLessonsByStudent() throws Exception {
        Student vasya = studentsDAO.findStudentById(1);
        Student ivan = studentsDAO.findStudentById(2);
        Student peter = studentsDAO.findStudentById(3);
        Lesson java = lessonsDAO.findLessonById(1);
        Lesson cpp = lessonsDAO.findLessonById(2);
        Lesson javascript = lessonsDAO.findLessonById(3);

        studentVisitsDAO.registerStudentAtLesson(vasya, java);
        studentVisitsDAO.registerStudentAtLesson(vasya, cpp);
        studentVisitsDAO.registerStudentAtLesson(vasya, javascript);
        studentVisitsDAO.registerStudentAtLesson(ivan, java);
        studentVisitsDAO.registerStudentAtLesson(ivan, cpp);
        studentVisitsDAO.registerStudentAtLesson(ivan, javascript);
        studentVisitsDAO.registerStudentAtLesson(peter, javascript);

        List<Lesson> vasyaVisited = studentVisitsDAO.findLessonsByStudent(vasya);

        assertEquals(3, vasyaVisited.size());
    }

    @Test
    public void testStudentsCascadeDelete() throws Exception {
        Student vasya = studentsDAO.findStudentById(1);
        Student ivan = studentsDAO.findStudentById(2);
        Student peter = studentsDAO.findStudentById(3);
        Lesson java = lessonsDAO.findLessonById(1);
        Lesson cpp = lessonsDAO.findLessonById(2);
        Lesson javascript = lessonsDAO.findLessonById(3);

        studentVisitsDAO.registerStudentAtLesson(vasya, java);
        studentVisitsDAO.registerStudentAtLesson(vasya, cpp);
        studentVisitsDAO.registerStudentAtLesson(vasya, javascript);
        studentVisitsDAO.registerStudentAtLesson(ivan, java);
        studentVisitsDAO.registerStudentAtLesson(ivan, cpp);
        studentVisitsDAO.registerStudentAtLesson(ivan, javascript);
        studentVisitsDAO.registerStudentAtLesson(peter, javascript);

        studentsDAO.deleteStudent(1);

        try (Statement stmt = dbService.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from Student_visits");
            int rows = 0;
            while (rs.next()) rows++;
            assertEquals(4, rows);
        }

        List<Lesson> deletedVasya = studentVisitsDAO.findLessonsByStudent(vasya);
        assertTrue(deletedVasya.isEmpty());
    }
}