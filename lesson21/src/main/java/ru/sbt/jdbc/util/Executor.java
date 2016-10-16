package ru.sbt.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Executor {

    public static void executeUpdate(Connection connection, String sql, ThrowableConsumer<PreparedStatement, SQLException> setter) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setter.accept(pstmt);
            pstmt.executeUpdate();
        }
    }
}
