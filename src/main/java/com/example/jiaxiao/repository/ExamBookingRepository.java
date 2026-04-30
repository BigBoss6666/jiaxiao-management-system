package com.example.jiaxiao.repository;

import com.example.jiaxiao.entity.ExamBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamBookingRepository extends JpaRepository<ExamBooking, Long> {

    List<ExamBooking> findByStudentId(Long studentId);

    List<ExamBooking> findBySlotId(Long slotId);

    Optional<ExamBooking> findByStudentIdAndSlotId(Long studentId, Long slotId);
}
