package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.StudentsDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentsDAOImpl implements StudentsDAO {

    private final Connection connection;

    public StudentsDAOImpl(Connection connection) {
        this.connection = connection;
    }

}
