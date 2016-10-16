package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentsDAO;
import ru.sbt.jdbc.dataset.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StudentsDAOImpl implements StudentsDAO {

    private final Connection connection;

    public StudentsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveStudent(Student student) {
        try (PreparedStatement pstmt = connection.prepareStatement(
                "insert into Students(name, surname) values(?, ?)")) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSurname());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save student: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Student> listStudents() {
        return null;
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
    public List<String> findStudentsBySurname(String surname) {
        return null;
    }

    @Override
    public List<String> findStudentsByNameAndSurname(String name, String surname) {
        return null;
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudent(long id) {

    }
}
