package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentsDAO;
import ru.sbt.jdbc.dataset.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
