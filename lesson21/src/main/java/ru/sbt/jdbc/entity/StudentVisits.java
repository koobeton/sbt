package ru.sbt.jdbc.entity;

public class StudentVisits {

    private final long studentId;
    private final long lessonId;

    public StudentVisits(long studentId, long lessonId) {
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    public long getStudentId() {
        return studentId;
    }

    public long getLessonId() {
        return lessonId;
    }

    @Override
    public String toString() {
        return String.format("%d : %d", studentId, lessonId);
    }
}
