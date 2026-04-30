package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "training_log")
@Getter
@Setter
@NoArgsConstructor
public class TrainingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联学员（多对一）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore   // 防止 JSON 死循环
    private Student student;

    // 训练科目，如：科目一、科目二
    @Column(nullable = false, length = 20)
    private String subject;

    // 本次增加的课时
    @Column(name = "hours_added", nullable = false)
    private Integer hoursAdded;

    // 记录时间
    @Column(name = "log_time", nullable = false)
    private LocalDateTime logTime = LocalDateTime.now();

    // 备注（可选）
    @Column(length = 200)
    private String remark;
}
