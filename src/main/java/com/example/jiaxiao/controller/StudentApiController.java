package com.example.jiaxiao.controller;

import com.example.jiaxiao.entity.Student;
import com.example.jiaxiao.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentApiController {

    private final StudentService studentService;

    // GET /api/students
    @GetMapping
    public List<Student> list() {
        return studentService.findAll();
    }

    // GET /api/students/{id}
    @GetMapping("/{id}")
    public Student detail(@PathVariable Long id) {
        return studentService.getById(id);
    }

    // POST /api/students
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    // PUT /api/students/{id}
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    // DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    // POST /api/students/{id}/next-subject
    @PostMapping("/{id}/next-subject")
    public Student nextSubject(@PathVariable Long id) {
        return studentService.nextSubject(id);
    }

    // POST /api/students/{id}/add-hours?hours=10
    @PostMapping("/{id}/add-hours")
    public Student addHours(@PathVariable Long id,
                            @RequestParam(defaultValue = "10") int hours) {
        return studentService.addHours(id, hours);
    }

    // POST /api/students/{id}/exams
    @PostMapping("/{id}/exams")
    public Student addExam(@PathVariable Long id,
                           @RequestParam String subject,
                           @RequestParam Integer score,
                           @RequestParam String date) {
        return studentService.addExam(id, subject, score, date);
    }

    // DELETE /api/students/{id}/exams
    @DeleteMapping("/{id}/exams")
    public Student clearExams(@PathVariable Long id) {
        return studentService.clearExams(id);
    }
}
