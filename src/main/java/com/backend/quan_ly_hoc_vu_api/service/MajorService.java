package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;

import java.util.List;

public interface MajorService {

    MajorDTO mapToDTO(Major major);
    List<MajorDTO> getAllMajors();

}
