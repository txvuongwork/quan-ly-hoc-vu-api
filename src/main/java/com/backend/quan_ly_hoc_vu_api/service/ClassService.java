package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Class;

public interface ClassService {

    ClassDTO createClass(ClassRequestDTO.CreateClassRequest request);
    ClassDTO mapToDTO(Class clazz);
    PaginationDTO<ClassDTO> getClassesWithFilter(ClassFilterCriteria criteria);
    ClassDTO updateClass(Long id, ClassRequestDTO.CreateClassRequest request);
    Class getClassById(Long id);
    void deleteClass(Long id);
    ClassDTO updateClassStatus(Long id, ClassRequestDTO.UpdateClassStatusRequest request);

}