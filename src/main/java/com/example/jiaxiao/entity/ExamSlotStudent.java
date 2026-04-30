// ExamSlotStudent.java
package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exam_slot_student")
@Data
public class ExamSlotStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonIgnoreProperties({"registrations"})  // 防止循环
    private ExamSlot slot;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"exams"})          // 视情况忽略
    private Student student;
}
