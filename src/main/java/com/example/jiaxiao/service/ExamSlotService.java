// ExamSlotService.java
package com.example.jiaxiao.service;

import com.example.jiaxiao.entity.ExamSlot;
import com.example.jiaxiao.entity.Student;

import java.util.List;

public interface ExamSlotService {

    List<ExamSlot> findAll();

    ExamSlot create(ExamSlot slot);

    ExamSlot assignStudent(Long slotId, Long studentId);

    List<Student> listStudents(Long slotId);
}
