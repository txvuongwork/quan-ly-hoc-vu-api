package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.MajorDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.MajorFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.MajorRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.repository.MajorRepository;
import com.backend.quan_ly_hoc_vu_api.service.BaseFilterService;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.MajorSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MajorServiceImpl extends BaseFilterService<Major, Long, MajorFilterCriteria, MajorDTO>
        implements MajorService {

    MajorRepository majorRepository;
    MajorSpecification majorSpecification;

    @Override
    public List<MajorDTO> getAllMajors() {
        return majorRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    @Transactional
    public MajorDTO createMajor(MajorRequestDTO.CreateMajorRequest request) {
        if (majorRepository.existsByMajorCode(request.getMajorCode())) {
            throw new BadRequestException(MAJOR_CODE_EXISTED_ERROR);
        }

        Major major = Major.builder()
                           .majorCode(request.getMajorCode())
                           .majorName(request.getMajorName())
                           .description(request.getDescription())
                           .build();

        Major savedMajor = majorRepository.save(major);

        return mapToDTO(savedMajor);
    }

    @Override
    @Transactional
    public MajorDTO updateMajor(Long id, MajorRequestDTO.CreateMajorRequest request) {
        Major existingMajor = getMajorById(id);

        if (!existingMajor.getMajorCode().equals(request.getMajorCode()) &&
            majorRepository.existsByMajorCode(request.getMajorCode())) {
            throw new BadRequestException(MAJOR_CODE_EXISTED_ERROR);
        }

        existingMajor.setMajorCode(request.getMajorCode());
        existingMajor.setMajorName(request.getMajorName());
        existingMajor.setDescription(request.getDescription());

        Major updatedMajor = majorRepository.save(existingMajor);

        return mapToDTO(updatedMajor);
    }

    @Override
    @Transactional
    public void deleteMajor(Long id) {
        Major major = getMajorById(id);

        if (majorRepository.hasSubjects(id)) {
            throw new BadRequestException(MAJOR_CANNOT_DELETE_ERROR);
        }

        majorRepository.delete(major);
    }

    @Override
    public Major getMajorById(Long majorId) {
        return majorRepository.findById(majorId)
                              .orElseThrow(() -> new BadRequestException(MAJOR_NOT_FOUND_ERROR));
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

    @Override
    public PaginationDTO<MajorDTO> getMajorsWithFilter(MajorFilterCriteria criteria) {
        return getWithFilter(criteria);
    }

    @Override
    protected JpaSpecificationExecutor<Major> getRepository() {
        return majorRepository;
    }

    @Override
    protected BaseSpecificationBuilder<Major, MajorFilterCriteria> getSpecificationBuilder() {
        return majorSpecification;
    }

    @Override
    protected Function<Major, MajorDTO> getEntityToDtoMapper() {
        return this::mapToDTO;
    }

}
