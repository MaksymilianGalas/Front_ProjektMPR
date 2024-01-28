package com.example.students.frontend;



import com.example.students.resource.CreateStudent;
import com.example.students.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/students-page")
public class StudentsPageController {

    private final StudentService studentService;

    public StudentsPageController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudentsPage(Model model) {
        var students = studentService.getAll();
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/add")
    public String returnAddStudentPage(Model model) {
        model.addAttribute("student", new CreateStudent());
        return "addStudent";
    }

    @PostMapping("/add")
    public String addStudentAndRedirectToMainPage(@ModelAttribute CreateStudent createStudent) {
        studentService.createStudent(createStudent);
        return "redirect:/students-page";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable UUID id) {
        model.addAttribute("student", new StudentDto(id));
        return "updateStudent";
    }
    @PostMapping("/edit/{id}")
    public String editStudent(@ModelAttribute StudentDto updateStudent, @PathVariable UUID id) {
        studentService.updateStudent(id, updateStudent);
        return "redirect:/students-page";
    }

    @PutMapping("/edit/{id}")
    public String editStudentPut(@ModelAttribute StudentDto updateStudent, @PathVariable UUID id) {
        studentService.updateStudent(id, updateStudent);
        return "redirect:/students-page";
    }


    @GetMapping("/search-all")
    public String searchAll(Model model) {
        var students = studentService.getAll();
        model.addAttribute("students", students);
        return "index";
    }


    @GetMapping("/search-by-email")
    public String searchStudentsByEmail(String email, Model model) {
        var students = studentService.getStudentsByEmail(email);
        model.addAttribute("students", students);
        return "indexes";
    }

}