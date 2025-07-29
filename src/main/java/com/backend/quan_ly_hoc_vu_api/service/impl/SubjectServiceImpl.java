package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.SubjectDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SubjectFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.SubjectRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import com.backend.quan_ly_hoc_vu_api.model.Subject;
import com.backend.quan_ly_hoc_vu_api.repository.SubjectRepository;
import com.backend.quan_ly_hoc_vu_api.service.BaseFilterService;
import com.backend.quan_ly_hoc_vu_api.service.MajorService;
import com.backend.quan_ly_hoc_vu_api.service.SubjectService;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.SubjectSpecification;
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
public class SubjectServiceImpl extends BaseFilterService<Subject, Long, SubjectFilterCriteria, SubjectDTO>
        implements SubjectService {

    SubjectRepository subjectRepository;
    MajorService majorService;
    SubjectSpecification subjectSpecification;

    @Override
    @Transactional
    public SubjectDTO createSubject(SubjectRequestDTO.CreateSubjectRequest request) {
        Major major = majorService.getMajorById(request.getMajorId());

        if (subjectRepository.existsBySubjectCode(request.getSubjectCode())) {
            throw new BadRequestException(SUBJECT_CODE_EXISTED_ERROR);
        }

        Subject subject = Subject.builder()
                                 .subjectCode(request.getSubjectCode())
                                 .subjectName(request.getSubjectName())
                                 .description(request.getDescription())
                                 .major(major)
                                 .credits(request.getCredits())
                                 .build();

        Subject savedSubject = subjectRepository.save(subject);

        return mapToDTO(savedSubject);
    }

    @Override
    @Transactional
    public SubjectDTO updateSubject(Long id, SubjectRequestDTO.CreateSubjectRequest request) {
        Subject existingSubject = getSubjectById(id);
        Major major = majorService.getMajorById(request.getMajorId());

        if (!existingSubject.getSubjectCode().equals(request.getSubjectCode()) &&
            subjectRepository.existsBySubjectCode(request.getSubjectCode())) {
            throw new BadRequestException(SUBJECT_CODE_EXISTED_ERROR);
        }

        existingSubject.setSubjectCode(request.getSubjectCode());
        existingSubject.setSubjectName(request.getSubjectName());
        existingSubject.setCredits(request.getCredits());
        existingSubject.setDescription(request.getDescription());
        existingSubject.setMajor(major);

        Subject updatedSubject = subjectRepository.save(existingSubject);

        return mapToDTO(updatedSubject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException(SUBJECT_NOT_FOUND_ERROR));
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        if (subjectRepository.hasClasses(id)) {
            throw new BadRequestException(SUBJECT_CANNOT_DELETE_ERROR);
        }
        subjectRepository.delete(subject);
    }

    @Override
    public SubjectDTO mapToDTO(Subject subject) {
        return SubjectDTO.builder()
                         .id(subject.getId())
                         .subjectCode(subject.getSubjectCode())
                         .subjectName(subject.getSubjectName())
                         .credits(subject.getCredits())
                         .description(subject.getDescription())
                         .major(majorService.mapToDTO(subject.getMajor()))
                         .createdAt(subject.getCreatedAt())
                         .build();
    }

    @Override
    public PaginationDTO<SubjectDTO> getSubjectsWithFilter(SubjectFilterCriteria criteria) {
        return getWithFilter(criteria);
    }

    @Override
    protected JpaSpecificationExecutor<Subject> getRepository() {
        return subjectRepository;
    }

    @Override
    protected BaseSpecificationBuilder<Subject, SubjectFilterCriteria> getSpecificationBuilder() {
        return subjectSpecification;
    }

    @Override
    protected Function<Subject, SubjectDTO> getEntityToDtoMapper() {
        return this::mapToDTO;
    }
}

