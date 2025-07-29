package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.SubjectDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SubjectFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.SubjectRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Subject;

import java.util.List;

public interface SubjectService {

    SubjectDTO createSubject(SubjectRequestDTO.CreateSubjectRequest request);
    SubjectDTO mapToDTO(Subject subject);
    PaginationDTO<SubjectDTO> getSubjectsWithFilter(SubjectFilterCriteria criteria);
    SubjectDTO updateSubject(Long id, SubjectRequestDTO.CreateSubjectRequest request);
    Subject getSubjectById(Long id);
    List<SubjectDTO> getAllSubjects();
    void deleteSubject(Long id);

}
