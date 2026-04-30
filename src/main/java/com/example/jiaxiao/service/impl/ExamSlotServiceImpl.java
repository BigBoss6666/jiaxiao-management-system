// ExamSlotServiceImpl.java
package com.example.jiaxiao.service.impl;

import com.example.jiaxiao.entity.ExamSlot;
import com.example.jiaxiao.entity.ExamSlotStudent;
import com.example.jiaxiao.entity.Student;
import com.example.jiaxiao.repository.ExamSlotRepository;
import com.example.jiaxiao.repository.ExamSlotStudentRepository;
import com.example.jiaxiao.repository.StudentRepository;
import com.example.jiaxiao.service.ExamSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamSlotServiceImpl implements ExamSlotService {

    private final ExamSlotRepository examSlotRepository;
    private final ExamSlotStudentRepository examSlotStudentRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<ExamSlot> findAll() {
        return examSlotRepository.findAll();
    }

    @Override
    @Transactional
    public ExamSlot create(ExamSlot slot) {
        if (slot.getRegisteredCount() == null) {
            slot.setRegisteredCount(0);
        }
        return examSlotRepository.save(slot);
    }

    @Override
    @Transactional
    public ExamSlot assignStudent(Long slotId, Long studentId) {
        ExamSlot slot = examSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("考试场次不存在：" + slotId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("学员不存在：" + studentId));

        // 容量校验（可选）
        if (slot.getCapacity() != null &&
                slot.getRegisteredCount() != null &&
                slot.getRegisteredCount() >= slot.getCapacity()) {
            throw new RuntimeException("报名人数已满");
        }

        // 是否重复报名简单略过（也可以先查一下 exam_slot_student 是否已有这条关系）
        ExamSlotStudent ess = new ExamSlotStudent();
        ess.setSlot(slot);
        ess.setStudent(student);
        examSlotStudentRepository.save(ess);

        slot.setRegisteredCount(slot.getRegisteredCount() + 1);
        return examSlotRepository.save(slot);
    }

    @Override
    public List<Student> listStudents(Long slotId) {
        ExamSlot slot = examSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("考试场次不存在：" + slotId));
        return examSlotStudentRepository.findBySlot(slot)
                .stream()
                .map(ExamSlotStudent::getStudent)
                .toList();
    }
}
