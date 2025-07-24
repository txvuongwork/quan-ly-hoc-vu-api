package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

    boolean existsBySubjectCode(String subjectCode);

}
