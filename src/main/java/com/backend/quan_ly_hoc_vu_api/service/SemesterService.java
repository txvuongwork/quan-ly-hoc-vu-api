package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.SemesterDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Semester;

import java.util.List;

public interface SemesterService {

    SemesterDTO mapToDTO(Semester semester);
    PaginationDTO<SemesterDTO> getSemestersWithFilter(SemesterFilterCriteria criteria);
    Semester getSemesterById(Long id);
    List<SemesterDTO> getAllSemesters();

}