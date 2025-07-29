package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.SemesterDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.service.SemesterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

}