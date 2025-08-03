package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Class c WHERE c.classCode = :classCode AND c.semester.id = :semesterId")
    boolean existsByClassCodeAndSemesterId(@Param("classCode") String classCode, @Param("semesterId") Long semesterId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Class c WHERE c.classCode = :classCode AND c.semester.id = :semesterId AND c.id != :classId")
    boolean existsByClassCodeAndSemesterIdAndIdNot(@Param("classCode") String classCode, @Param("semesterId") Long semesterId, @Param("classId") Long classId);

    @Modifying
    @Query("UPDATE Class c SET c.status = :toStatus WHERE c.semester.id = :semesterId AND c.status = :fromStatus")
    int updateStatusBySemesterAndCurrentStatus(@Param("semesterId") Long semesterId,
                                               @Param("fromStatus") ClassStatus fromStatus,
                                               @Param("toStatus") ClassStatus toStatus);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Class c WHERE c.semester.id = :semesterId AND c.status NOT IN (:allowedStatuses)")
    boolean existsBySemesterIdAndStatusNotIn(@Param("semesterId") Long semesterId, @Param("allowedStatuses") java.util.List<ClassStatus> allowedStatuses);

    @Query("SELECT c FROM Class c WHERE c.status = :classStatus " +
           "AND c.semester.status = :semesterStatus " +
           "AND (:semesterId IS NULL OR c.semester.id = :semesterId) " +
           "AND (SELECT COUNT(e) FROM Enrollment e WHERE e.clazz.id = c.id AND e.status = 'ENROLLED') < c.maxStudents " +
           "AND (:userId IS NULL OR NOT EXISTS (SELECT 1 FROM Enrollment e WHERE e.clazz.id = c.id AND e.student.id = :userId AND e.status = 'ENROLLED'))")
    List<Class> findAvailableClassesForRegistration(@Param("classStatus") ClassStatus classStatus,
                                                    @Param("semesterStatus") com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus semesterStatus,
                                                    @Param("semesterId") Long semesterId,
                                                    @Param("userId") Long userId);

}