package com.valentinpopescu98.testing.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {
    private static final String DUMMY_NAME_1 = "dummy1";
    private static final String DUMMY_NAME_2 = "dummy2";
    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldFindStudentByName() {
        // Given
        Student predictedStudent = new Student(DUMMY_NAME_1);
        studentRepository.save(predictedStudent);

        // When
        Student foundStudent = studentRepository.findByName(DUMMY_NAME_1).get();

        // Then
        assertThat(foundStudent).usingRecursiveComparison()
                                .isEqualTo(predictedStudent);
    }

    @Test
    void shouldUpdateStudentById() {
        // Given
        Student savedStudent = new Student(DUMMY_NAME_1);
        studentRepository.save(savedStudent);

        // When
        studentRepository.updateById(savedStudent.getId(), DUMMY_NAME_2);

        // Then
        Student updatedStudent = studentRepository.findByName(DUMMY_NAME_2).get();

        Student predictedStudent = new Student(savedStudent.getId(), DUMMY_NAME_2);
        assertThat(updatedStudent).usingRecursiveComparison()
                                  .isEqualTo(predictedStudent);
    }
}
