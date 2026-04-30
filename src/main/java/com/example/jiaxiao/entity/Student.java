package com.example.jiaxiao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 基本信息
    private String name;
    private String classType;
    private String phone;
    private String idCard;

    // 进度信息
    private String currentSubject;  // 科目一 / 科目二 ...
    private Integer hours;          // 已学课时
    private String status;          // 在读 / 休学 ...

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamRecord> exams = new ArrayList<>();
}
