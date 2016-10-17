package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentVisitsDAO;
import ru.sbt.jdbc.entity.Lesson;
import ru.sbt.jdbc.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static ru.sbt.jdbc.util.Executor.executeBatchUpdate;

public class StudentVisitsDAOImpl implements StudentVisitsDAO {

    private final Connection connection;

    public StudentVisitsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registerStudentAtLesson(Student student, Lesson lesson) {
        String sql = "insert into Student_visits(student_id, lesson_id) values(?, ?)";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(1, student.getId());
                pstmt.setLong(2, lesson.getId());
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to register student at lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeStudentFromLesson(Student student, Lesson lesson) {
        String sql = "delete from Student_visits where student_id = ? and lesson_id = ?";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(1, student.getId());
                pstmt.setLong(2, lesson.getId());
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to remove student from lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> findStudentsByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public List<Lesson> findLessonsByStudent(Student student) {
        return null;
    }
}
