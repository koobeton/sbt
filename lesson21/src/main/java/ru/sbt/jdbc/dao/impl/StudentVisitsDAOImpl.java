package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentVisitsDAO;
import ru.sbt.jdbc.entity.Lesson;
import ru.sbt.jdbc.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static ru.sbt.jdbc.dao.handler.LessonsResultHandler.getLessonsResultHandler;
import static ru.sbt.jdbc.dao.handler.StudentsResultHandler.getStudentsResultHandler;
import static ru.sbt.jdbc.util.Executor.executeBatchUpdate;
import static ru.sbt.jdbc.util.Executor.executeQuery;

public class StudentVisitsDAOImpl implements StudentVisitsDAO {

    private final Connection connection;

    public StudentVisitsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registerStudentAtLesson(Student student, Lesson lesson) {
        String sql = "insert into Student_visits(student_id, lesson_id) values(?, ?)";
        try {
            executeUpdate(sql, student, lesson);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to register student at lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeStudentFromLesson(Student student, Lesson lesson) {
        String sql = "delete from Student_visits where student_id = ? and lesson_id = ?";
        try {
            executeUpdate(sql, student, lesson);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to remove student from lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> findStudentsByLesson(Lesson lesson) {
        String sql = "select id, name, surname from Students, Student_visits where id = student_id and lesson_id = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setLong(1, lesson.getId()), getStudentsResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find students by lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Lesson> findLessonsByStudent(Student student) {
        String sql = "select id, subject, date from Lessons, Student_visits where id = lesson_id and student_id = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setLong(1, student.getId()), getLessonsResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find lessons by student: " + e.getMessage(), e);
        }
    }

    private void executeUpdate(String sql, Student student, Lesson lesson) throws SQLException {
        executeBatchUpdate(connection, sql, pstmt -> {
            pstmt.setLong(1, student.getId());
            pstmt.setLong(2, lesson.getId());
            pstmt.addBatch();
        });
    }
}
