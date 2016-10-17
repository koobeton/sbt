package ru.sbt.jdbc.dao.impl;

import ru.sbt.jdbc.dao.LessonsDAO;
import ru.sbt.jdbc.entity.Lesson;
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

public class LessonDAOImpl implements LessonsDAO {

    private final Connection connection;

    public LessonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveLesson(Lesson lesson) {
        saveLessons(Collections.singletonList(lesson));
    }

    @Override
    public void saveLessons(List<Lesson> lessons) {
        String sql = "insert into Lessons(subject, date) values(?, ?)";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                for (Lesson lesson : lessons) {
                    pstmt.setString(1, lesson.getSubject());
                    pstmt.setLong(2, lesson.getDate());
                    pstmt.addBatch();
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to save lesson(s): " + e.getMessage(), e);
        }
    }

    @Override
    public List<Lesson> listLessons() {
        String sql = "select * from Lessons";
        try {
            return executeQuery(connection, sql, getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get lessons list: " + e.getMessage(), e);
        }
    }

    @Override
    public Lesson findLessonById(long id) {
        String sql = "select * from Lessons where id = ?";
        try {
            return selectOne(executeQuery(connection, sql, pstmt -> pstmt.setLong(1, id), getResultHandler()));
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find lesson by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Lesson> findLessonsBySubject(String subject) {
        String sql = "select * from Lessons where subject = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setString(1, subject), getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find lessons by subject: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Lesson> findLessonsByDate(long date) {
        String sql = "select * from Lessons where date = ?";
        try {
            return executeQuery(connection, sql, pstmt -> pstmt.setLong(1, date), getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find lessons by date: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Lesson> findLessonsBySubjectAndDate(String subject, long date) {
        String sql = "select * from Lessons where subject = ? and date = ?";
        try {
            return executeQuery(connection, sql, pstmt -> {
                pstmt.setString(1, subject);
                pstmt.setLong(2, date);
            }, getResultHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find lessons by subject and date: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateLesson(Lesson lesson) {
        if (lesson.getId() == Lesson.UNDEFINED_ID)
            throw new IllegalArgumentException("Unable to update lesson with undefined id, try to save it first");
        String sql = "update Lessons set subject = ?, date = ? where id = ?";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(3, lesson.getId());
                pstmt.setString(1, lesson.getSubject());
                pstmt.setLong(2, lesson.getDate());
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update lesson: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteLesson(long id) {
        String sql = "delete from Lessons where id = ?";
        try {
            executeBatchUpdate(connection, sql, pstmt -> {
                pstmt.setLong(1, id);
                pstmt.addBatch();
            });
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete lesson: " + e.getMessage(), e);
        }
    }

    private ThrowableFunction<ResultSet, List<Lesson>, SQLException> getResultHandler() {
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
