package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.repository.MajorRepository;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    MajorRepository majorRepository;

    @Override
    public List<MajorDTO> getAllMajors() {
        return majorRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public MajorDTO mapToDTO(Major major) {
        return MajorDTO.builder()
                       .id(major.getId())
                       .majorCode(major.getMajorCode())
                       .majorName(major.getMajorName())
                       .description(major.getDescription())
                       .createdAt(major.getCreatedAt())
                       .build();
    }

}
