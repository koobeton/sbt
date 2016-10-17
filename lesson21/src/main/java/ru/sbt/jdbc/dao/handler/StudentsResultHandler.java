package ru.sbt.jdbc.dao.handler;

import ru.sbt.jdbc.entity.Student;
import ru.sbt.jdbc.util.ThrowableFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsResultHandler {

    public static ThrowableFunction<ResultSet, List<Student>, SQLException> getStudentsResultHandler() {
        return rs -> {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                students.add(new Student(id, name, surname));
            }
            return students;
        };
    }
}
