package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;

public interface MajorService {

    MajorDTO mapToDTO(Major major);

}
