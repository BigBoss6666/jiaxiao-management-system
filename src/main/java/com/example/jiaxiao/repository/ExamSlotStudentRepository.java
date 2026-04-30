package com.example.jiaxiao.repository;

import com.example.jiaxiao.entity.ExamSlotStudent;
import com.example.jiaxiao.entity.ExamSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamSlotStudentRepository extends JpaRepository<ExamSlotStudent, Long> {
    List<ExamSlotStudent> findBySlot(ExamSlot slot);
}