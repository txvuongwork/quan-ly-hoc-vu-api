package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

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
