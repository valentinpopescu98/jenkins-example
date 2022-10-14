package com.valentinpopescu98.testing.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private static final String DUMMY_NAME_1 = "dummy1";
    private static final String DUMMY_NAME_2 = "dummy2";
    private static final long DUMMY_ID = 1;

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void shouldFetchStudentById() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.of(student));

        // When
        studentService.getStudent(DUMMY_ID);

        // Then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).findById(idCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertThat(capturedId).isEqualTo(DUMMY_ID);
    }

    @Test
    void shouldThrowExceptionWhenFetchingStudentWithIdNotFound() {
        // Given
        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> studentService.getStudent(DUMMY_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Student with ID %d not found.", DUMMY_ID));
    }

    @Test
    void shouldFetchAllStudents() {
        // When
        studentService.getAllStudents();

        // Then
        verify(studentRepository).findAll();
    }

    @Test
    void shouldAddStudent() {
        // Given
        Student predictedStudent = new Student(DUMMY_NAME_1);

        // When
        studentService.addStudent(predictedStudent);

        // Then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());

        Student capturedStudent = studentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(predictedStudent);
    }

    @Test
    void shouldThrowExceptionWhenAddingStudentWithNameAlreadyTaken() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        given(studentRepository.findByName(anyString()))
                .willReturn(Optional.of(student));

        // Then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Student with name %s exists.", DUMMY_NAME_1));

        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldRemoveStudent() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        given(studentRepository.findById(DUMMY_ID))
                .willReturn(Optional.of(student));

        // When
        studentService.removeStudent(DUMMY_ID);

        // Then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).delete(studentCaptor.capture());

        Student capturedStudent = studentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void shouldThrowExceptionWhenRemovingStudentWithIdNotFound() {
        // Given
        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> studentService.removeStudent(DUMMY_ID))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Student with ID %d not found.", DUMMY_ID));

        verify(studentRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateStudent() {
        // Given
        Student oldStudent = new Student(DUMMY_NAME_1);
        Student newStudent = new Student(DUMMY_NAME_2);

        given(studentRepository.findById(DUMMY_ID))
                .willReturn(Optional.of(oldStudent));

        given(studentRepository.findByName(DUMMY_NAME_2))
                .willReturn(Optional.empty());

        // When
        studentService.updateStudent(DUMMY_ID, newStudent);

        // Then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> newStudentNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(studentRepository).updateById(idCaptor.capture(), newStudentNameCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertThat(capturedId).isEqualTo(DUMMY_ID);

        String capturedNewStudentName = newStudentNameCaptor.getValue();
        assertThat(capturedNewStudentName).isEqualTo(DUMMY_NAME_2);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStudentWithIdNotFound() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> studentService.updateStudent(DUMMY_ID, student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Student with ID %d not found.", DUMMY_ID));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStudentWithNewStudentNameAlreadyTaken() {
        // Given
        Student student = new Student(DUMMY_NAME_1);

        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.of(student));

        given(studentRepository.findByName(anyString()))
                .willReturn(Optional.of(student));

        // Then
        assertThatThrownBy(() -> studentService.updateStudent(DUMMY_ID, student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Student with name %s already exists.", DUMMY_NAME_1));
    }

    @Test
    void shouldUpdateStudentNameWithNewStudentNameWhenNameNotGiven() {
        // Given
        Student oldStudent = new Student(DUMMY_NAME_1);

        given(studentRepository.findById(anyLong()))
                .willReturn(Optional.of(oldStudent));

        given(studentRepository.findByName(nullable(String.class)))
                .willReturn(Optional.empty());

        // When
        Student newStudent = new Student(null);

        studentService.updateStudent(DUMMY_ID, newStudent);

        // Then
        ArgumentCaptor<String> newStudentNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(studentRepository).updateById(anyLong(), newStudentNameCaptor.capture());

        String capturedNewStudentName = newStudentNameCaptor.getValue();
        assertThat(capturedNewStudentName).isEqualTo(DUMMY_NAME_1);
    }
}