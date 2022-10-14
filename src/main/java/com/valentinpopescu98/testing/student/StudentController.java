package com.valentinpopescu98.testing.student;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public String removeStudent(@PathVariable("studentId") Long studentId) {
        return studentService.removeStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public Student updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestBody Student student) {
        return studentService.updateStudent(studentId, student);
    }
}
