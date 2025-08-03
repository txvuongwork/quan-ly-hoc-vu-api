package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.TeacherClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.service.ClassService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClassController {

    ClassService classService;

    @GetMapping
    public ResponseEntity<PaginationDTO<ClassDTO>> getClasses(@ModelAttribute ClassFilterCriteria criteria) {
        return ResponseEntity.ok(classService.getClassesWithFilter(criteria));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable Long id) {
        Class clazz = classService.getClassById(id);
        return ResponseEntity.ok(classService.mapToDTO(clazz));
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@Valid @RequestBody ClassRequestDTO.CreateClassRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(classService.createClass(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id,
                                                @Valid @RequestBody ClassRequestDTO.CreateClassRequest request) {
        return ResponseEntity.ok(classService.updateClass(id, request));
    }

    @GetMapping("/available-for-registration")
    public ResponseEntity<List<ClassDTO>> getAvailableClassesForRegistration(
            @RequestParam(value = "semesterId", required = false) Long semesterId) {
        List<ClassDTO> availableClasses = classService.getAvailableClassesForRegistration(semesterId);
        return ResponseEntity.ok(availableClasses);
    }

    @GetMapping("/enrolled")
    public ResponseEntity<List<ClassDTO>> getEnrolledClasses(
            @RequestParam(value = "semesterId", required = false) Long semesterId) {
        List<ClassDTO> enrolledClasses = classService.getEnrolledClasses(semesterId);
        return ResponseEntity.ok(enrolledClasses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ClassDTO> updateClassStatus(
            @PathVariable Long id,
            @Valid @RequestBody ClassRequestDTO.UpdateClassStatusRequest request) {
        ClassDTO updatedClass = classService.updateClassStatus(id, request);
        return ResponseEntity.ok(updatedClass);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<ClassDTO>> getTeacherClasses(
            @RequestParam(value = "semesterId", required = false) Long semesterId) {

        Long teacherId = SecurityUtils.getCurrentUserId();

        List<ClassDTO> classes = classService.getTeacherClasses(teacherId, semesterId);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity<List<UserDTO>> getClassStudents(@PathVariable Long classId) {
        List<UserDTO> students = classService.getClassStudents(classId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/student")
    public ResponseEntity<List<ClassDTO>> getStudentClasses(
            @RequestParam(value = "semesterId", required = false) Long semesterId) {
        Long studentId = SecurityUtils.getCurrentUserId();

        List<ClassDTO> classes = classService.getStudentClasses(studentId, semesterId);
        return ResponseEntity.ok(classes);
    }

}