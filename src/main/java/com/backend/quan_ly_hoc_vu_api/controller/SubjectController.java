package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.SubjectDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SubjectFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.SubjectRequestDTO;
import com.backend.quan_ly_hoc_vu_api.service.SubjectService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subjects")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubjectController {

    SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody @Valid SubjectRequestDTO.CreateSubjectRequest request) {
        return ResponseEntity.ok(subjectService.createSubject(request));
    }

    @GetMapping
    public ResponseEntity<PaginationDTO<SubjectDTO>> getSubjects(@ModelAttribute SubjectFilterCriteria criteria) {
        return ResponseEntity.ok(subjectService.getSubjectsWithFilter(criteria));
    }

}
