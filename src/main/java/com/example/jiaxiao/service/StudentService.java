package com.example.jiaxiao.service;

import com.example.jiaxiao.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student getById(Long id);

    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);

    Student nextSubject(Long id);

    Student addHours(Long id, int hours);

    Student addExam(Long id, String subject, Integer score, String date);

    Student clearExams(Long id);
}
