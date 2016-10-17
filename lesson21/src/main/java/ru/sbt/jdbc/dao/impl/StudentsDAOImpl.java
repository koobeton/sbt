package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentsDAO;
import ru.sbt.jdbc.entity.Student;
import ru.sbt.jdbc.util.ThrowableFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.sbt.jdbc.util.Executor.executeBatchUpdate;
import static ru.sbt.jdbc.util.Executor.executeQuery;
import static ru.sbt.jdbc.util.Selector.selectOne;

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
            executeBatchUpdate(connection, sql, pstmt -> {
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
            return executeQuery(connection, sql, getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get students list: " + e.getMessage(), e);
        }
    }

    @Override
    public Student findStudentById(long id) {
        String sql = "select * from Students where id = ?";
        try {
            return selectOne(executeQuery(connection, sql, pstmt -> pstmt.setLong(1, id), getResultHandler()));
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find student by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        String sql = "select * from Students where name = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setString(1, name), getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find students by name: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> findStudentsBySurname(String surname) {
        String sql = "select * from Students where surname = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setString(1, surname), getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find students by surname: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> findStudentsByNameAndSurname(String name, String surname) {
        String sql = "select * from Students where name = ? and surname = ?";
        try {
            return executeQuery(connection, sql, pstmt -> {
                pstmt.setString(1, name);
                pstmt.setString(2, surname);
            }, getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find students by name and surname: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (student.getId() == Student.UNDEFINED_ID)
            throw new IllegalArgumentException("Unable to update student with undefined id, try to save it first");
        String sql = "update Students set name = ?, surname = ? where id = ?";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(3, student.getId());
                pstmt.setString(1, student.getName());
                pstmt.setString(2, student.getSurname());
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update student: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStudent(long id) {
        String sql = "delete from Students where id = ?";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(1, id);
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete student: " + e.getMessage(), e);
        }
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
