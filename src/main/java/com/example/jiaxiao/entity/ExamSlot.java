// ExamSlot.java
package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_slot")
@Data
public class ExamSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 科目一/科目二...
    private String subject;

    @Column(name = "exam_date")
    private LocalDate examDate;

    // 上午场 / 下午场
    private String session;

    // 最大容量
    private Integer capacity;
    private Integer bookedCount; // 已报名人数

    // 已报名人数
    @Column(name = "registered_count")
    private Integer registeredCount = 0;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore   // 防止 JSON 无限嵌套
    private List<ExamSlotStudent> registrations = new ArrayList<>();
}
