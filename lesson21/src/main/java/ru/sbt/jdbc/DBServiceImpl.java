package ru.sbt.jdbc;

import ru.sbt.jdbc.dao.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DBServiceImpl implements DBService {

    private final Connection connection;

    public DBServiceImpl(String url) {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to database: " + e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during closing connection to database: " + e.getMessage(), e);
        }
    }

    @Override
    public void createTables(DAO... tables) {
        Stream.of(tables).forEach(DAO::createTable);

        //TODO
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Students" +
                    "(" +
                    "id bigint auto_increment," +
                    "name varchar(255) not null," +
                    "surname varchar(255) not null," +
                    "primary key(id)" +
                    ");");

            stmt.executeUpdate("CREATE TABLE Lessons" +
                    "(" +
                    "id bigint auto_increment," +
                    "subject varchar(255) not null," +
                    "date bigint not null," +
                    "primary key(id)" +
                    ");");

            stmt.executeUpdate("CREATE TABLE Student_visits" +
                    "(" +
                    "student_id bigint," +
                    "lesson_id bigint," +
                    "foreign key(student_id) references Students(id) on update cascade on delete cascade," +
                    "foreign key(lesson_id) references Lessons(id) on update cascade on delete cascade" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create table: " + e.getMessage(), e);
        }
    }
}
