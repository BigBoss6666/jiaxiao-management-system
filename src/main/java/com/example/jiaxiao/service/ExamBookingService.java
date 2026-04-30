package com.example.jiaxiao.service;

import com.example.jiaxiao.entity.ExamBooking;

public interface ExamBookingService {

    // 学员报名某一场考试
    ExamBooking createBooking(Long studentId, Long slotId);

    // 取消报名（简单一点，直接删除）
    void cancelBooking(Long bookingId);
}
