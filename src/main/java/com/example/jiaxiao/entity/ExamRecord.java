package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "exam_record")
public class ExamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 科目
    private String subject;

    // 分数
    private Integer score;

    // 第几次考试
    private Integer times;

    // 考试日期
    @Column(name = "exam_date")
    private LocalDate examDate;

    // 是否通过：数据库列 is_pass，JSON 字段 isPass
    @Column(name = "is_pass")
    private Boolean isPass;

    // 多对一学生
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;
}
