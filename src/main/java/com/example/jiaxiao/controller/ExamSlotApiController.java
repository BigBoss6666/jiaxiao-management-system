// ExamSlotApiController.java
package com.example.jiaxiao.controller;

import com.example.jiaxiao.entity.ExamSlot;
import com.example.jiaxiao.entity.Student;
import com.example.jiaxiao.service.ExamSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exam-slots")
public class ExamSlotApiController {

    private final ExamSlotService examSlotService;

    // GET /api/exam-slots
    @GetMapping
    public List<ExamSlot> list() {
        return examSlotService.findAll();
    }

    // POST /api/exam-slots
    @PostMapping
    public ExamSlot create(@RequestBody ExamSlot slot) {
        return examSlotService.create(slot);
    }

    // POST /api/exam-slots/{id}/assign?studentId=1
    @PostMapping("/{id}/assign")
    public ExamSlot assign(@PathVariable Long id,
                           @RequestParam Long studentId) {
        return examSlotService.assignStudent(id, studentId);
    }

    // GET /api/exam-slots/{id}/students
    @GetMapping("/{id}/students")
    public List<Student> listStudents(@PathVariable Long id) {
        return examSlotService.listStudents(id);
    }
}
