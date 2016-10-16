package ru.sbt.jdbc.dao;

import ru.sbt.jdbc.dataset.Student;

import java.util.List;

public interface StudentsDAO {

    void saveStudent(Student student);

    void saveStudents(List<Student> students);

    List<Student> listStudents();

    Student findStudentById(long id);

    List<Student> findStudentsByName(String name);

    List<Student> findStudentsBySurname(String surname);

    List<Student> findStudentsByNameAndSurname(String name, String surname);

    void updateStudent(Student student);

    void deleteStudent(long id);
}
