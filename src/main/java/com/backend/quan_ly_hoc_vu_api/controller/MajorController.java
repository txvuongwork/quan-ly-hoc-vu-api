package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.MajorFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.MajorRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MajorController {

    MajorService majorService;

    @GetMapping("/all")
    public ResponseEntity<List<MajorDTO>> getAllMajors() {
        return ResponseEntity.ok(majorService.getAllMajors());
    }

    @PostMapping
    public ResponseEntity<MajorDTO> createMajor(
            @RequestBody @Valid MajorRequestDTO.CreateMajorRequest request) {
        return ResponseEntity.ok(majorService.createMajor(request));
    }

    @GetMapping
    public ResponseEntity<PaginationDTO<MajorDTO>> getMajors(@ModelAttribute MajorFilterCriteria criteria) {
        return ResponseEntity.ok(majorService.getMajorsWithFilter(criteria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorDTO> updateMajor(
            @PathVariable Long id,
            @RequestBody @Valid MajorRequestDTO.CreateMajorRequest request) {
        return ResponseEntity.ok(majorService.updateMajor(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorDTO> getMajor(@PathVariable Long id) {
        Major major = majorService.getMajorById(id);
        return ResponseEntity.ok(majorService.mapToDTO(major));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable Long id) {
        majorService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }

}
