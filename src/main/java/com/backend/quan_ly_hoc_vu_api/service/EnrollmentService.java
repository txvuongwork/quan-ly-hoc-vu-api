package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.EnrollmentDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.EnrollmentRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.model.Enrollment;

import java.util.List;

public interface EnrollmentService {

    /**
     * Register a student for a class
     */
    EnrollmentDTO registerForClass(EnrollmentRequestDTO.CreateEnrollmentRequest request);
//
//    /**
//     * Cancel enrollment (unregister from class)
//     */
//    void cancelEnrollment(Long enrollmentId);
//
//    /**
//     * Get all enrollments for current student in current semester
//     */
//    List<EnrollmentDTO> getMyCurrentEnrollments();
//
//    /**
//     * Get classes by semester and enrollment status for current user
//     */
//    List<ClassDTO> getClassesByStatus(Long semesterId, EnrollmentStatus status);
//
//    /**
//     * Get enrollment by ID
//     */
//    Enrollment getEnrollmentById(Long enrollmentId);

    /**
     * Map Enrollment entity to DTO
     */
    EnrollmentDTO mapToDTO(Enrollment enrollment);

//    /**
//     * Get students enrolled in a class by status
//     */
//    List<UserDTO> getStudentsByClassAndStatus(Long classId, EnrollmentStatus status);

}