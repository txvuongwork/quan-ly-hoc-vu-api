package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.service.ClassService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClassController {

    ClassService classService;

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(
            @RequestBody @Valid ClassRequestDTO.CreateClassRequest request) {
        return ResponseEntity.ok(classService.createClass(request));
    }

    @GetMapping
    public ResponseEntity<PaginationDTO<ClassDTO>> getClasses(@ModelAttribute ClassFilterCriteria criteria) {
        return ResponseEntity.ok(classService.getClassesWithFilter(criteria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(
            @PathVariable Long id,
            @RequestBody @Valid ClassRequestDTO.CreateClassRequest request) {
        return ResponseEntity.ok(classService.updateClass(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable Long id) {
        Class clazz = classService.getClassById(id);
        return ResponseEntity.ok(classService.mapToDTO(clazz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

}