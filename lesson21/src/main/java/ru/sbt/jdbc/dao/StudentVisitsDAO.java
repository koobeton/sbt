package ru.sbt.jdbc.dao;

import ru.sbt.jdbc.dataset.Lesson;
import ru.sbt.jdbc.dataset.Student;

import java.util.List;

public interface StudentVisitsDAO {

    void registerStudentAtLesson(Student student, Lesson lesson);

    void removeStudentFromLesson(Student student, Lesson lesson);

    List<Student> findStudentsByLesson(Lesson lesson);

    List<Lesson> findLessonsByStudent(Student student);
}
