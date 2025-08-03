package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.EnrollmentDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.EnrollmentRequestDTO;
import com.backend.quan_ly_hoc_vu_api.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EnrollmentController {

    EnrollmentService enrollmentService;

    /**
     * Register for a class
     */
    @PostMapping("/register")
    public ResponseEntity<EnrollmentDTO> registerForClass(
            @RequestBody @Valid EnrollmentRequestDTO.CreateEnrollmentRequest request) {
        EnrollmentDTO enrollment = enrollmentService.registerForClass(request);
        return ResponseEntity.ok(enrollment);
    }

//    /**
//     * Cancel enrollment (unregister from class)
//     */
//    @DeleteMapping("/{enrollmentId}")
//    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long enrollmentId) {
//        enrollmentService.cancelEnrollment(enrollmentId);
//        return ResponseEntity.noContent().build();
//    }
//
//    /**
//     * Get my current enrollments in current semester
//     */
//    @GetMapping("/my-classes")
//    public ResponseEntity<List<EnrollmentDTO>> getMyCurrentEnrollments() {
//        List<EnrollmentDTO> enrollments = enrollmentService.getMyCurrentEnrollments();
//        return ResponseEntity.ok(enrollments);
//    }
//
//    /**
//     * Get classes by semester and enrollment status for current user
//     */
//    @GetMapping("/classes")
//    public ResponseEntity<List<ClassDTO>> getClassesByStatus(
//            @RequestParam Long semesterId,
//            @RequestParam EnrollmentStatus status) {
//        List<ClassDTO> classes = enrollmentService.getClassesByStatus(semesterId, status);
//        return ResponseEntity.ok(classes);
//    }
//
//    /**
//     * Get students enrolled in a class by status
//     */
//    @GetMapping("/students")
//    public ResponseEntity<List<UserDTO>> getStudentsByClass(
//            @RequestParam Long classId,
//            @RequestParam EnrollmentStatus status) {
//        List<UserDTO> students = enrollmentService.getStudentsByClassAndStatus(classId, status);
//        return ResponseEntity.ok(students);
//    }

}