package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {

    /**
     * Find attendance session by code
     */
    Optional<AttendanceSession> findByAttendanceCode(String attendanceCode);

    /**
     * Check if attendance code exists
     */
    boolean existsByAttendanceCode(String attendanceCode);

    /**
     * Find all attendance sessions by class id, ordered by created date desc
     */
    @Query("SELECT a FROM AttendanceSession a WHERE a.classEntity.id = :classId ORDER BY a.createdAt DESC")
    List<AttendanceSession> findByClassIdOrderByCreatedAtDesc(@Param("classId") Long classId);

    /**
     * Find active attendance sessions by class id
     */
    @Query("SELECT a FROM AttendanceSession a WHERE a.classEntity.id = :classId AND a.isActive = true ORDER BY a.createdAt DESC")
    List<AttendanceSession> findActiveByClassId(@Param("classId") Long classId);

}