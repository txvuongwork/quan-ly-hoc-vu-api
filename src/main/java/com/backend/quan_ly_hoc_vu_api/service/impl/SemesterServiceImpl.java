package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.SemesterDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.SemesterRequestDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import com.backend.quan_ly_hoc_vu_api.repository.ClassRepository;
import com.backend.quan_ly_hoc_vu_api.repository.SemesterRepository;
import com.backend.quan_ly_hoc_vu_api.service.BaseFilterService;
import com.backend.quan_ly_hoc_vu_api.service.SemesterService;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.SemesterSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SemesterServiceImpl extends BaseFilterService<Semester, Long, SemesterFilterCriteria, SemesterDTO>
        implements SemesterService {

    SemesterRepository semesterRepository;
    SemesterSpecification semesterSpecification;
    ClassRepository classRepository;

    private static final Map<SemesterStatus, Set<SemesterStatus>> VALID_STATUS_TRANSITIONS = Map.of(
            SemesterStatus.DRAFT, Set.of(SemesterStatus.REGISTRATION_OPEN),
            SemesterStatus.REGISTRATION_OPEN, Set.of(SemesterStatus.REGISTRATION_CLOSED),
            SemesterStatus.REGISTRATION_CLOSED, Set.of(SemesterStatus.IN_PROGRESS),
            SemesterStatus.IN_PROGRESS, Set.of(SemesterStatus.COMPLETED),
            SemesterStatus.COMPLETED, Set.of() // No transitions from COMPLETED
    );

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
    @Transactional
    public SemesterDTO createSemester(SemesterRequestDTO.CreateSemesterRequest request) {
        // Validate semester code uniqueness
        if (semesterRepository.existsBySemesterCode(request.getSemesterCode())) {
            throw new BadRequestException(SEMESTER_CODE_EXISTED_ERROR);
        }

        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate()) ||
            request.getEndDate().isEqual(request.getStartDate())) {
            throw new BadRequestException(SEMESTER_DATE_RANGE_INVALID_ERROR);
        }

        if (semesterRepository.existsOverlappingSemester(request.getStartDate(), request.getEndDate())) {
            throw new BadRequestException(SEMESTER_DATE_OVERLAP_ERROR);
        }

        // Create semester entity
        Semester semester = Semester.builder()
                                    .semesterCode(request.getSemesterCode())
                                    .semesterName(request.getSemesterName())
                                    .startDate(request.getStartDate())
                                    .endDate(request.getEndDate())
                                    .status(SemesterStatus.DRAFT)
                                    .build();

        // Save and return DTO
        Semester savedSemester = semesterRepository.save(semester);
        return mapToDTO(savedSemester);
    }

    @Override
    @Transactional
    public SemesterDTO updateSemester(Long id, SemesterRequestDTO.UpdateSemesterRequest request) {
        // Find existing semester
        Semester existingSemester = getSemesterById(id);

        // Validate semester code uniqueness (exclude current semester)
        if (!existingSemester.getSemesterCode().equals(request.getSemesterCode()) &&
            semesterRepository.existsBySemesterCode(request.getSemesterCode())) {
            throw new BadRequestException(SEMESTER_CODE_EXISTED_ERROR);
        }

        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate()) ||
            request.getEndDate().isEqual(request.getStartDate())) {
            throw new BadRequestException(SEMESTER_DATE_RANGE_INVALID_ERROR);
        }

        if (semesterRepository.existsOverlappingSemesterExcluding(
                request.getStartDate(), request.getEndDate(), id)) {
            throw new BadRequestException(SEMESTER_DATE_OVERLAP_ERROR);
        }

        // Update semester fields
        existingSemester.setSemesterCode(request.getSemesterCode());
        existingSemester.setSemesterName(request.getSemesterName());
        existingSemester.setStartDate(request.getStartDate());
        existingSemester.setEndDate(request.getEndDate());

        // Save and return DTO
        Semester updatedSemester = semesterRepository.save(existingSemester);
        return mapToDTO(updatedSemester);
    }

    @Override
    @Transactional
    public SemesterDTO updateSemesterStatus(Long id, SemesterRequestDTO.UpdateSemesterStatusRequest request) {
        // Find existing semester
        Semester semester = getSemesterById(id);
        SemesterStatus currentStatus = semester.getStatus();
        SemesterStatus newStatus = request.getStatus();

        // Validate status transition
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            log.warn("Invalid status transition for semester {}: {} -> {}", id, currentStatus, newStatus);
            throw new BadRequestException(SEMESTER_STATUS_TRANSITION_INVALID_ERROR);
        }

        // Additional validation for REGISTRATION_CLOSED -> IN_PROGRESS
        if (currentStatus == SemesterStatus.REGISTRATION_CLOSED && newStatus == SemesterStatus.IN_PROGRESS) {
            List<ClassStatus> allowedStatuses = List.of(ClassStatus.CONFIRMED, ClassStatus.CANCELLED);
            if (classRepository.existsBySemesterIdAndStatusNotIn(id, allowedStatuses)) {
                throw new BadRequestException(SEMESTER_CANNOT_START_UNCONFIRMED_CLASSES_ERROR);
            }
        }

        // Update semester status
        semester.setStatus(newStatus);
        Semester updatedSemester = semesterRepository.save(semester);

        // Handle status-specific business logic
        handleStatusTransitionSideEffects(semester, currentStatus, newStatus);

        log.info("Updated semester {} status: {} -> {}", id, currentStatus, newStatus);
        return mapToDTO(updatedSemester);
    }

    @Override
    public SemesterDTO mapToDTO(Semester semester) {
        return SemesterDTO.builder()
                          .id(semester.getId())
                          .semesterCode(semester.getSemesterCode())
                          .semesterName(semester.getSemesterName())
                          .startDate(semester.getStartDate())
                          .endDate(semester.getEndDate())
                          .status(semester.getStatus())
                          .createdAt(semester.getCreatedAt())
                          .updatedAt(semester.getUpdatedAt())
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

    /**
     * Validate if status transition is allowed
     */
    private boolean isValidStatusTransition(SemesterStatus from, SemesterStatus to) {
        if (from == to) {
            return true; // Same status is always valid
        }
        return VALID_STATUS_TRANSITIONS.getOrDefault(from, Set.of()).contains(to);
    }

    /**
     * Handle business logic when semester status changes
     */
    private void handleStatusTransitionSideEffects(Semester semester, SemesterStatus from, SemesterStatus to) {
        switch (to) {
            case REGISTRATION_OPEN:
                log.info("Opening registration for semester {}, updating class statuses", semester.getId());
                int draftToOpen = classRepository.updateStatusBySemesterAndCurrentStatus(
                        semester.getId(), ClassStatus.DRAFT, ClassStatus.OPEN_FOR_REGISTRATION);
                log.info("Updated {} classes from DRAFT to OPEN_FOR_REGISTRATION", draftToOpen);
                break;

            case IN_PROGRESS:
                log.info("Starting semester {}, updating confirmed classes to IN_PROGRESS", semester.getId());
                int confirmedToProgress = classRepository.updateStatusBySemesterAndCurrentStatus(
                        semester.getId(), ClassStatus.CONFIRMED, ClassStatus.IN_PROGRESS);
                log.info("Updated {} classes from CONFIRMED to IN_PROGRESS", confirmedToProgress);
                break;

            case COMPLETED:
                log.info("Completing semester {}, updating classes to COMPLETED", semester.getId());
                int progressToCompleted = classRepository.updateStatusBySemesterAndCurrentStatus(
                        semester.getId(), ClassStatus.IN_PROGRESS, ClassStatus.COMPLETED);
                log.info("Updated {} classes from IN_PROGRESS to COMPLETED", progressToCompleted);
                break;

            default:
                break;
        }
    }

}