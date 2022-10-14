package com.valentinpopescu98.testing.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student getStudent(Long studentId) {
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        boolean studentNotFound = foundStudent.isEmpty();

        if (studentNotFound) {
            throw new IllegalStateException(
                    String.format("Student with ID %d not found.", studentId));
        }

        Student student = foundStudent.get();

        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students;
    }

    public Student addStudent(Student student) {
        Optional<Student> foundStudent = studentRepository.findByName(student.getName());
        boolean studentExists = foundStudent.isPresent();

        if (studentExists) {
            throw new IllegalStateException(
                    String.format("Student with name %s exists.", student.getName()));
        }

        studentRepository.save(student);

        return student;
    }

    public String removeStudent(Long studentId) {
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        boolean studentNotFound = foundStudent.isEmpty();

        if (studentNotFound) {
            throw new IllegalStateException(
                    String.format("Student with ID %d not found.", studentId));
        }

        Student student = foundStudent.get();

        studentRepository.delete(student);

        return student + " deleted";
    }

    public Student updateStudent(Long studentId, Student student) {
        Optional<Student> oldStudentOptional = studentRepository.findById(studentId);
        Optional<Student> newStudentOptional = studentRepository.findByName(student.getName());

        boolean oldStudentNotFound = oldStudentOptional.isEmpty();
        boolean newStudentExists = newStudentOptional.isPresent();

        if (oldStudentNotFound) {
            throw new IllegalStateException(
                    String.format("Student with ID %d not found.", studentId));
        }

        if (newStudentExists) {
            throw new IllegalStateException(
                    String.format("Student with name %s already exists.", student.getName()));
        }

        Student oldStudent = oldStudentOptional.get();

        if (student.getName() == null) {
            student.setName(oldStudent.getName());
        }

        studentRepository.updateById(studentId, student.getName());

        return student;
    }
}
