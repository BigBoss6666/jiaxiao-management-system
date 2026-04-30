package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "exam_booking",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_booking_student_slot", columnNames = {"student_id", "slot_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ExamBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 学员（多对一）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    // 考试场次（多对一）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    @JsonIgnore
    private ExamSlot slot;

    // 报名状态：已报名 / 已考试 / 取消
    @Column(nullable = false, length = 20)
    private String status = "已报名";

    // 报名时间
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
