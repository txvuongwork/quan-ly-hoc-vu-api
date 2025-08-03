package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Check if student already attended this session
     */
    @Query("SELECT COUNT(a) > 0 FROM Attendance a WHERE a.attendanceSession.id = :sessionId AND a.student.id = :studentId")
    boolean isStudentAlreadyAttended(@Param("sessionId") Long sessionId, @Param("studentId") Long studentId);

    /**
     * Find attendance by session and student
     */
    @Query("SELECT a FROM Attendance a WHERE a.attendanceSession.id = :sessionId AND a.student.id = :studentId")
    Optional<Attendance> findBySessionAndStudent(@Param("sessionId") Long sessionId, @Param("studentId") Long studentId);

    /**
     * Find all attendances for a session, ordered by attended time
     */
    @Query("SELECT a FROM Attendance a WHERE a.attendanceSession.id = :sessionId ORDER BY a.attendedAt ASC")
    List<Attendance> findBySessionIdOrderByAttendedAt(@Param("sessionId") Long sessionId);

    /**
     * Count attendances for a session
     */
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceSession.id = :sessionId")
    long countBySessionId(@Param("sessionId") Long sessionId);

    /**
     * Find attendances by class id
     */
    @Query("SELECT a FROM Attendance a WHERE a.attendanceSession.classEntity.id = :classId ORDER BY a.attendanceSession.createdAt DESC, a.attendedAt ASC")
    List<Attendance> findByClassIdOrderBySessionAndAttendedAt(@Param("classId") Long classId);

}