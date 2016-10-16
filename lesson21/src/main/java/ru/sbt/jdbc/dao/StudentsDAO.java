package ru.sbt.jdbc.dao;

import ru.sbt.jdbc.dataset.Student;

import java.util.List;

public interface StudentsDAO {

    void saveStudent(Student student);

    List<Student> listStudents();

    Student findStudentById(long id);

    List<Student> findStudentsByName(String name);

    List<String> findStudentsBySurname(String surname);

    List<String> findStudentsByNameAndSurname(String name, String surname);

    void updateStudent(Student student);

    void deleteStudent(long id);
}
