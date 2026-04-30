package com.example.jiaxiao.repository;

import com.example.jiaxiao.entity.TrainingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingLogRepository extends JpaRepository<TrainingLog, Long> {

    // 按学员查询训练记录（时间倒序）
    List<TrainingLog> findByStudentIdOrderByLogTimeDesc(Long studentId);
}
