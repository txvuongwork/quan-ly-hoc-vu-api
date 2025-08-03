package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.TeacherClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.model.Class;

import java.util.List;

public interface ClassService {

    ClassDTO createClass(ClassRequestDTO.CreateClassRequest request);
    ClassDTO updateClass(Long id, ClassRequestDTO.CreateClassRequest request);
    ClassDTO mapToDTO(Class clazz);
    PaginationDTO<ClassDTO> getClassesWithFilter(ClassFilterCriteria criteria);
    Class getClassById(Long id);
    List<ClassDTO> getAllClasses();
    List<ClassDTO> getAvailableClassesForRegistration(Long semesterId);
    List<ClassDTO> getEnrolledClasses(Long semesterId);
    ClassDTO updateClassStatus(Long id, ClassRequestDTO.UpdateClassStatusRequest request);
    List<ClassDTO> getTeacherClasses(Long teacherId, Long semesterId);
    List<UserDTO> getClassStudents(Long classId);
    List<ClassDTO> getStudentClasses(Long studentId, Long semesterId);

}