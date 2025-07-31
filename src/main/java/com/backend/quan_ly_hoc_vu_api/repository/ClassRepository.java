package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {

    boolean existsByClassCode(String classCode);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.classEntity.id = :classId AND e.status = :status")
    long countEnrollmentsByClassIdAndStatus(@Param("classId") Long classId, @Param("status") EnrollmentStatus status);

}