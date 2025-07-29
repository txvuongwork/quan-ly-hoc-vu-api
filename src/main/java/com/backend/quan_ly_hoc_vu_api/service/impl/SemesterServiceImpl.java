package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.SemesterDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import com.backend.quan_ly_hoc_vu_api.repository.SemesterRepository;
import com.backend.quan_ly_hoc_vu_api.service.BaseFilterService;
import com.backend.quan_ly_hoc_vu_api.service.SemesterService;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.SemesterSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.SEMESTER_NOT_FOUND_ERROR;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SemesterServiceImpl extends BaseFilterService<Semester, Long, SemesterFilterCriteria, SemesterDTO>
        implements SemesterService {

    SemesterRepository semesterRepository;
    SemesterSpecification semesterSpecification;

    @Override
    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id)
                                 .orElseThrow(() -> new BadRequestException(SEMESTER_NOT_FOUND_ERROR));
    }

    @Override
    public List<SemesterDTO> getAllSemesters() {
        return semesterRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public SemesterDTO mapToDTO(Semester semester) {
        return SemesterDTO.builder()
                          .id(semester.getId())
                          .semesterName(semester.getSemesterName())
                          .year(semester.getYear())
                          .semesterNumber(semester.getSemesterNumber())
                          .semesterStart(semester.getSemesterStart())
                          .semesterEnd(semester.getSemesterEnd())
                          .registrationStart(semester.getRegistrationStart())
                          .registrationEnd(semester.getRegistrationEnd())
                          .status(semester.getStatus())
                          .createdAt(semester.getCreatedAt())
                          .build();
    }

    @Override
    public PaginationDTO<SemesterDTO> getSemestersWithFilter(SemesterFilterCriteria criteria) {
        return getWithFilter(criteria);
    }

    @Override
    protected JpaSpecificationExecutor<Semester> getRepository() {
        return semesterRepository;
    }

    @Override
    protected BaseSpecificationBuilder<Semester, SemesterFilterCriteria> getSpecificationBuilder() {
        return semesterSpecification;
    }

    @Override
    protected Function<Semester, SemesterDTO> getEntityToDtoMapper() {
        return this::mapToDTO;
    }
}