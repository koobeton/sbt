package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentsDAO;
import ru.sbt.jdbc.dataset.Student;
import ru.sbt.jdbc.util.Executor;
import ru.sbt.jdbc.util.ThrowableFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsDAOImpl implements StudentsDAO {

    private final Connection connection;

    public StudentsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveStudent(Student student) {
        saveStudents(Collections.singletonList(student));
    }

    @Override
    public void saveStudents(List<Student> students) {
        String sql = "insert into Students(name, surname) values(?, ?)";
        try {
            Executor.executeBatchUpdate(connection, sql, pstmt -> {
                for (Student student : students) {
                    pstmt.setString(1, student.getName());
                    pstmt.setString(2, student.getSurname());
                    pstmt.addBatch();
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save student(s): " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> listStudents() {
        String sql = "select * from Students";
        try {
            return Executor.executeQuery(connection, sql, getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get students list: " + e.getMessage(), e);
        }
    }

    @Override
    public Student findStudentById(long id) {
        return null;
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        return null;
    }

    @Override
    public List<Student> findStudentsBySurname(String surname) {
        return null;
    }

    @Override
    public List<Student> findStudentsByNameAndSurname(String name, String surname) {
        return null;
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudent(long id) {

    }

    private ThrowableFunction<ResultSet, List<Student>, SQLException> getResultHandler() {
        return rs -> {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                students.add(new Student(id, name, surname));
            }
            return students;
        };
    }
}
