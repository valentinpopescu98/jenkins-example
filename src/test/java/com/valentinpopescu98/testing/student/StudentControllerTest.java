package com.valentinpopescu98.testing.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    private static final String DUMMY_NAME_1 = "dummy1";
    private static final long DUMMY_ID = 1;

    @Mock
    private StudentService studentService;
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        studentController = new StudentController(studentService);
    }

    @Test
    void shouldFetchStudentById() {
        // When
        studentController.getStudent(DUMMY_ID);

        // Then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentService).getStudent(idCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertThat(capturedId).isEqualTo(DUMMY_ID);
    }

    @Test
    void shouldFetchAllStudents() {
        // When
        studentController.getAllStudents();

        // Then
        verify(studentService).getAllStudents();
    }

    @Test
    void shouldAddStudent() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        // When
        studentController.addStudent(student);

        // Then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).addStudent(studentCaptor.capture());

        Student capturedStudent = studentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void shouldRemoveStudent() {
        // When
        studentController.removeStudent(DUMMY_ID);

        // Then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentService).removeStudent(idCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertThat(capturedId).isEqualTo(DUMMY_ID);
    }

    @Test
    void shouldUpdateStudent() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        // When
        studentController.updateStudent(DUMMY_ID, student);

        // Then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).updateStudent(idCaptor.capture(), studentCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertThat(capturedId).isEqualTo(DUMMY_ID);

        Student capturedStudent = studentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }
}