package ru.sbt.jdbc.util;

import java.sql.*;

public class Executor {

    public static void executeBatchUpdate(
            Connection connection,
            String sql,
            ThrowableConsumer<PreparedStatement, SQLException> batchParameterAdder)
            throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            batchParameterAdder.accept(pstmt);
            pstmt.executeBatch();
        }
    }

    public static <R> R executeQuery(
            Connection connection,
            String sql,
            ThrowableFunction<ResultSet, R, SQLException> resultHandler)
            throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            return resultHandler.apply(rs);
        }
    }

    public static <R> R executeQuery(
            Connection connection,
            String sql,
            ThrowableConsumer<PreparedStatement, SQLException> parameterSetter,
            ThrowableFunction<ResultSet, R, SQLException> resultHandler)
            throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            parameterSetter.accept(pstmt);
            ResultSet rs = pstmt.executeQuery();
            return resultHandler.apply(rs);
        }
    }
}
