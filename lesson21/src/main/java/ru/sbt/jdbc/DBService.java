package ru.sbt.jdbc;

import ru.sbt.jdbc.dao.DAO;

import java.sql.Connection;

public interface DBService {

    Connection getConnection();

    void shutdown();

    void createTables(DAO... tables);
}
