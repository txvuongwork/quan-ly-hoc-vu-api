package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.model.Enrollment;
import com.backend.quan_ly_hoc_vu_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment> {

    /**
     * Check if student is already enrolled in a class
     */
    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.student.id = :userId AND e.clazz.id = :classId AND e.status = :status")
    boolean isStudentEnrolledInClass(@Param("userId") Long userId,
                                     @Param("classId") Long classId,
                                     @Param("status") EnrollmentStatus status);

    /**
     * Count enrolled students in a class
     */
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.clazz.id = :classId AND e.status = :status")
    long countEnrolledStudents(@Param("classId") Long classId, @Param("status") EnrollmentStatus status);

    /**
     * Find all enrollments for a student in a specific semester
     */
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :userId AND e.clazz.semester.id = :semesterId AND e.status = :status")
    List<Enrollment> findStudentEnrollmentsInSemester(@Param("userId") Long userId,
                                                      @Param("semesterId") Long semesterId,
                                                      @Param("status") EnrollmentStatus status);

    /**
     * Calculate total credits enrolled by student in a semester
     */
    @Query("SELECT COALESCE(SUM(e.clazz.subject.credits), 0) FROM Enrollment e WHERE e.student.id = :userId AND e.clazz.semester.id = :semesterId AND e.status = :status")
    int getTotalCreditsEnrolledInSemester(@Param("userId") Long userId,
                                          @Param("semesterId") Long semesterId,
                                          @Param("status") EnrollmentStatus status);

    /**
     * Find enrollment by user and class
     */
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :userId AND e.clazz.id = :classId AND e.status = :status")
    Optional<Enrollment> findByUserAndClassAndStatus(@Param("userId") Long userId,
                                                     @Param("classId") Long classId,
                                                     @Param("status") EnrollmentStatus status);

    /**
     * Update enrollment status for all enrollments in a class
     */
    @Modifying
    @Query("UPDATE Enrollment e SET e.status = :newStatus WHERE e.clazz.id = :classId AND e.status = :currentStatus")
    int updateEnrollmentStatusByClassId(@Param("classId") Long classId,
                                        @Param("currentStatus") EnrollmentStatus currentStatus,
                                        @Param("newStatus") EnrollmentStatus newStatus);

    /**
     * Find classes for a student by semester and enrollment status
     */
    @Query("SELECT e.clazz FROM Enrollment e WHERE e.student.id = :userId AND e.clazz.semester.id = :semesterId AND e.status = :status")
    List<Class> findClassesByUserAndSemesterAndStatus(@Param("userId") Long userId,
                                                      @Param("semesterId") Long semesterId,
                                                      @Param("status") EnrollmentStatus status);

    /**
     * Find users by class and enrollment status
     */
    @Query("SELECT e.student FROM Enrollment e WHERE e.clazz.id = :classId AND e.status = :status")
    List<User> findUsersByClassAndStatus(@Param("classId") Long classId,
                                         @Param("status") EnrollmentStatus status);

    @Query("SELECT e.clazz FROM Enrollment e WHERE e.status = 'ENROLLED' " +
           "AND (:semesterId IS NULL OR e.clazz.semester.id = :semesterId) " +
           "AND (:userId IS NULL OR e.student.id = :userId)")
    List<Class> findEnrolledClasses(@Param("semesterId") Long semesterId,
                                    @Param("userId") Long userId);

}