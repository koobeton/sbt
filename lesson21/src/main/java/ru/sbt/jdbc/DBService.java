package ru.sbt.jdbc;

import java.sql.Connection;

public interface DBService {

    Connection getConnection();

    void shutdown();

    void createTables();
}
