package ru.sbt.jdbc.dao;

import ru.sbt.jdbc.dataset.Lesson;

import java.util.List;

public interface LessonsDAO {

    void saveLesson(Lesson lesson);

    void saveLessons(List<Lesson> lessons);

    List<Lesson> listLesson();

    Lesson findLessonById(long id);

    List<Lesson> findLessonsBySubject(String subject);

    List<Lesson> findLessonsByDate(long date);

    List<Lesson> findLessonsBySubjectAndDate(String subject, long date);

    void updateLesson(Lesson lesson);

    void deleteLesson(long id);
}
