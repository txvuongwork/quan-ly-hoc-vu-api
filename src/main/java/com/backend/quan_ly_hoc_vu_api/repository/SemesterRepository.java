package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

    boolean existsByYearAndSemesterNumber(Integer year, Integer semesterNumber);

}