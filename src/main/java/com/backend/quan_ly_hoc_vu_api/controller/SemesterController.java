package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.SemesterDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.SemesterRequestDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import com.backend.quan_ly_hoc_vu_api.service.SemesterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SemesterController {

    SemesterService semesterService;

    @GetMapping
    public ResponseEntity<PaginationDTO<SemesterDTO>> getSemesters(@ModelAttribute SemesterFilterCriteria criteria) {
        return ResponseEntity.ok(semesterService.getSemestersWithFilter(criteria));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SemesterDTO>> getAllSemesters() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterDTO> getSemesterById(@PathVariable Long id) {
        Semester semester = semesterService.getSemesterById(id);
        return ResponseEntity.ok(semesterService.mapToDTO(semester));
    }

    @PostMapping
    public ResponseEntity<SemesterDTO> createSemester(@Valid @RequestBody SemesterRequestDTO.CreateSemesterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(semesterService.createSemester(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterDTO> updateSemester(@PathVariable Long id,
                                                      @Valid @RequestBody SemesterRequestDTO.UpdateSemesterRequest request) {
        return ResponseEntity.ok(semesterService.updateSemester(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SemesterDTO> updateSemesterStatus(@PathVariable Long id,
                                                            @Valid @RequestBody SemesterRequestDTO.UpdateSemesterStatusRequest request) {
        return ResponseEntity.ok(semesterService.updateSemesterStatus(id, request));
    }


}