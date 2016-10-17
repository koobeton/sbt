package ru.sbt.jdbc.dao.handler;

import ru.sbt.jdbc.entity.Lesson;
import ru.sbt.jdbc.util.ThrowableFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonsResultHandler {

    public static ThrowableFunction<ResultSet, List<Lesson>, SQLException> getResultHandler() {
        return rs -> {
            List<Lesson> lessons = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String subject = rs.getString("subject");
                long date = rs.getLong("date");
                lessons.add(new Lesson(id, subject, date));
            }
            return lessons;
        };
    }
}
