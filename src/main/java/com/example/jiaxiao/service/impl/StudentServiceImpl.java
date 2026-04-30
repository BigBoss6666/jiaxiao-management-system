package com.example.jiaxiao.service.impl;

import com.example.jiaxiao.entity.ExamRecord;
import com.example.jiaxiao.entity.Student;
import com.example.jiaxiao.entity.TrainingLog;
import com.example.jiaxiao.repository.StudentRepository;
import com.example.jiaxiao.repository.TrainingLogRepository;
import com.example.jiaxiao.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TrainingLogRepository trainingLogRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("学员不存在"));
    }

    @Override
    public Student create(Student student) {
        // 初始化字段
        if (student.getStatus() == null) {
            student.setStatus("在读");
        }
        if (student.getCurrentSubject() == null) {
            student.setCurrentSubject("科目一");
        }
        if (student.getHours() == null) {
            student.setHours(0);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student param) {
        Student db = getById(id);
        db.setName(param.getName());
        db.setClassType(param.getClassType());
        db.setPhone(param.getPhone());
        db.setIdCard(param.getIdCard());
        // status / currentSubject / hours 不强制改，由前端或其他接口改
        return studentRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        Student db = getById(id);
        studentRepository.delete(db);
    }

    @Override
    public Student nextSubject(Long id) {
        Student stu = getById(id);
        String[] order = {"科目一", "科目二", "科目三", "科目四", "结业"};
        String cur = stu.getCurrentSubject();
        int idx = 0;
        for (int i = 0; i < order.length; i++) {
            if (order[i].equals(cur)) {
                idx = i;
                break;
            }
        }
        String next = order[(idx + 1) % order.length];
        stu.setCurrentSubject(next);
        stu.setStatus("结业".equals(next) ? "已结业" : next + "训练中");
        return studentRepository.save(stu);
    }

    @Override
    @Transactional
    public Student addHours(Long id, int hours) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学员不存在，id=" + id));

        int oldHours = (s.getHours() == null ? 0 : s.getHours());
        s.setHours(oldHours + hours);

        // 1) 先保存学员
        Student saved = studentRepository.save(s);

        // 2) 写训练记录
        TrainingLog log = new TrainingLog();
        log.setStudent(saved);
        log.setSubject(saved.getCurrentSubject() == null ? "未知科目" : saved.getCurrentSubject());
        log.setHoursAdded(hours);
        log.setLogTime(LocalDateTime.now());
        log.setRemark("前端点击「+10课时」自动生成");

        trainingLogRepository.save(log);

        return saved;
    }

    @Override
    @Transactional
    public Student addExam(Long id, String subject, Integer score, String date) {
        // 1. 一定要从数据库查出“真实存在”的 student
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学员不存在，id=" + id));

        // 2. 构造考试记录，绑上 student
        ExamRecord exam = new ExamRecord();
        exam.setStudent(student);
        exam.setSubject(subject);
        exam.setScore(score);

        // 第几次考试：已有记录数量 + 1
        List<ExamRecord> exams = student.getExams();
        if (exams == null) {
            exams = new ArrayList<>();
            student.setExams(exams);
        }
        exam.setTimes(exams.size() + 1);

        // 通过与否
        exam.setIsPass(score != null && score >= 90);

        // 日期
        exam.setExamDate(LocalDate.parse(date)); // 前端传的是 "YYYY-MM-DD"

        // 3. 加入到 student 的列表中，让级联保存 exam_record
        exams.add(exam);

        // 4. 保存 student（级联插入 exam_record），此时 student.id 一定是存在的
        return studentRepository.save(student);
    }

    @Override
    public Student clearExams(Long id) {
        Student stu = getById(id);
        stu.getExams().clear();
        return studentRepository.save(stu);
    }

}
