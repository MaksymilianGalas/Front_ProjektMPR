package com.example.students.resource;

import com.example.students.frontend.StudentDto;
import com.example.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentsResource {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody @Validated CreateStudent student) {
        studentService.createStudent(student);
    }


    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStudent(@PathVariable UUID id, @RequestBody @Validated StudentDto studentDto) {
        studentService.updateStudent(id,studentDto);
    }

    @DeleteMapping
    public void deleteByName(@PathVariable String name) {
        studentService.deleteByName(name);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable UUID id){ return studentService.getStudentById(id); }

    @GetMapping
    public List<StudentDto> getAll() {
        return studentService.getAll();
    }
}