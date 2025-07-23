package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
