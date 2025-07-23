package com.backend.quan_ly_hoc_vu_api.repository;

import com.backend.quan_ly_hoc_vu_api.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
