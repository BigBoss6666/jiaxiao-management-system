package com.example.jiaxiao.service.impl;

import com.example.jiaxiao.entity.ExamBooking;
import com.example.jiaxiao.entity.ExamSlot;
import com.example.jiaxiao.entity.Student;
import com.example.jiaxiao.repository.ExamBookingRepository;
import com.example.jiaxiao.repository.ExamSlotRepository;
import com.example.jiaxiao.repository.StudentRepository;
import com.example.jiaxiao.service.ExamBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExamBookingServiceImpl implements ExamBookingService {

    private final ExamBookingRepository examBookingRepository;
    private final StudentRepository studentRepository;
    private final ExamSlotRepository examSlotRepository;

    @Override
    @Transactional
    public ExamBooking createBooking(Long studentId, Long slotId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("学员不存在, id=" + studentId));
        ExamSlot slot = examSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("考试场次不存在, id=" + slotId));

        // 防止重复报名同一场
        examBookingRepository.findByStudentIdAndSlotId(studentId, slotId)
                .ifPresent(b -> {
                    throw new RuntimeException("该学员已经报名过此场考试");
                });

        // 检查容量
        if (slot.getBookedCount() != null && slot.getCapacity() != null &&
                slot.getBookedCount() >= slot.getCapacity()) {
            throw new RuntimeException("该场次已满，无法报名");
        }

        // 创建报名记录
        ExamBooking booking = new ExamBooking();
        booking.setStudent(student);
        booking.setSlot(slot);
        booking.setStatus("已报名");

        ExamBooking saved = examBookingRepository.save(booking);

        // 已报名人数 +1
        int booked = slot.getBookedCount() == null ? 0 : slot.getBookedCount();
        slot.setBookedCount(booked + 1);
        examSlotRepository.save(slot);

        return saved;
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        ExamBooking booking = examBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("报名记录不存在,id=" + bookingId));

        ExamSlot slot = booking.getSlot();
        // 报名人数 -1（最少减到 0）
        if (slot != null && slot.getBookedCount() != null && slot.getBookedCount() > 0) {
            slot.setBookedCount(slot.getBookedCount() - 1);
            examSlotRepository.save(slot);
        }

        examBookingRepository.delete(booking);
    }
}
