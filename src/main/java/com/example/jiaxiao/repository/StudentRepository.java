package com.example.jiaxiao.repository;

import com.example.jiaxiao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
