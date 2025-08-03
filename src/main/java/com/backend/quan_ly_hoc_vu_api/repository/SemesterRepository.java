package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

    boolean existsBySemesterCode(String semesterCode);

    @Query("SELECT COUNT(s) > 0 FROM Semester s WHERE " +
           "(s.startDate <= :endDate AND s.endDate >= :startDate)")
    boolean existsOverlappingSemester(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(s) > 0 FROM Semester s WHERE " +
           "(s.startDate <= :endDate AND s.endDate >= :startDate) AND s.id != :excludeId")
    boolean existsOverlappingSemesterExcluding(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate,
                                               @Param("excludeId") Long excludeId);

}