package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.MajorFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.MajorRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Major;

import java.util.List;

public interface MajorService {

    MajorDTO mapToDTO(Major major);
    List<MajorDTO> getAllMajors();
    Major getMajorById(Long majorId);
    MajorDTO createMajor(MajorRequestDTO.CreateMajorRequest request);
    PaginationDTO<MajorDTO> getMajorsWithFilter(MajorFilterCriteria criteria);
    MajorDTO updateMajor(Long id, MajorRequestDTO.CreateMajorRequest request);
    void deleteMajor(Long id);

}
