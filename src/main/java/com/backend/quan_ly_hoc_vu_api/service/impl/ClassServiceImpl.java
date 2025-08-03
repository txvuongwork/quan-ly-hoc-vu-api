package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.ClassScheduleDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.TeacherClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.DayOfWeek;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.*;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.repository.ClassRepository;
import com.backend.quan_ly_hoc_vu_api.repository.EnrollmentRepository;
import com.backend.quan_ly_hoc_vu_api.service.*;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.ClassSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl extends BaseFilterService<Class, Long, ClassFilterCriteria, ClassDTO>
        implements ClassService {

    ClassRepository classRepository;
    SubjectService subjectService;
    SemesterService semesterService;
    UserService userService;
    ClassSpecification classSpecification;
    EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public ClassDTO createClass(ClassRequestDTO.CreateClassRequest request) {
        // Get related entities
        Subject subject = subjectService.getSubjectById(request.getSubjectId());
        Semester semester = semesterService.getSemesterById(request.getSemesterId());
        User teacher = userService.getUserByIdOrThrow(request.getTeacherId());

        // Validate business rules
        validateClassRequest(request, null);

        // Validate class code uniqueness in semester
        if (classRepository.existsByClassCodeAndSemesterId(request.getClassCode(), request.getSemesterId())) {
            throw new BadRequestException(CLASS_CODE_EXISTED_ERROR);
        }

        // Create class entity
        Class clazz = Class.builder()
                           .classCode(request.getClassCode())
                           .subject(subject)
                           .semester(semester)
                           .teacher(teacher)
                           .maxStudents(request.getMaxStudents())
                           .minStudents(request.getMinStudents())
                           .processPercent(request.getProcessPercent())
                           .midtermPercent(request.getMidtermPercent())
                           .finalPercent(request.getFinalPercent())
                           .status(ClassStatus.DRAFT)
                           .build();

        // Create and add schedules
        List<ClassSchedule> schedules = request.getSchedules().stream()
                                               .map(scheduleRequest -> ClassSchedule.builder()
                                                                                    .clazz(clazz)
                                                                                    .dayOfWeek(DayOfWeek.fromValue(scheduleRequest.getDayOfWeek()))
                                                                                    .startPeriod(scheduleRequest.getStartPeriod())
                                                                                    .endPeriod(scheduleRequest.getEndPeriod())
                                                                                    .build())
                                               .toList();

        clazz.getSchedules().addAll(schedules);

        // Save and return DTO
        Class savedClass = classRepository.save(clazz);
        return mapToDTO(savedClass);
    }

    @Override
    @Transactional
    public ClassDTO updateClassStatus(Long id, ClassRequestDTO.UpdateClassStatusRequest request) {
        // Find existing class
        Class existingClass = getClassById(id);
        ClassStatus currentStatus = existingClass.getStatus();
        ClassStatus newStatus = request.getStatus();

        // Validate current status must be OPEN_FOR_REGISTRATION
        if (currentStatus != ClassStatus.OPEN_FOR_REGISTRATION) {
            throw new BadRequestException(CLASS_STATUS_UPDATE_NOT_ALLOWED_ERROR);
        }

        // Validate new status must be CONFIRMED or CANCELLED
        if (newStatus != ClassStatus.CONFIRMED && newStatus != ClassStatus.CANCELLED) {
            throw new BadRequestException(CLASS_STATUS_UPDATE_INVALID_TARGET_ERROR);
        }

        // Update class status
        existingClass.setStatus(newStatus);
        Class updatedClass = classRepository.save(existingClass);

        // Handle enrollment status changes based on new class status
        handleClassStatusChangeEnrollments(id, newStatus);

        log.info("Updated class {} status: {} -> {}", id, currentStatus, newStatus);
        return mapToDTO(updatedClass);
    }

    /**
     * Handle enrollment status changes when class status changes
     */
    private void handleClassStatusChangeEnrollments(Long classId, ClassStatus newStatus) {
        switch (newStatus) {
            case CANCELLED:
                // Change all ENROLLED enrollments to DROPPED
                int droppedCount = enrollmentRepository.updateEnrollmentStatusByClassId(
                        classId, EnrollmentStatus.ENROLLED, EnrollmentStatus.DROPPED);
                log.info("Updated {} enrollments from ENROLLED to DROPPED for cancelled class {}",
                         droppedCount, classId);
                break;

            case CONFIRMED:
                // Change all ENROLLED enrollments to COMPLETED
                int completedCount = enrollmentRepository.updateEnrollmentStatusByClassId(
                        classId, EnrollmentStatus.ENROLLED, EnrollmentStatus.COMPLETED);
                log.info("Updated {} enrollments from ENROLLED to COMPLETED for confirmed class {}",
                         completedCount, classId);
                break;

            default:
                break;
        }
    }

    @Override
    public List<ClassDTO> getTeacherClasses(Long teacherId, Long semesterId) {
        ClassFilterCriteria criteria = ClassFilterCriteria.builder()
                                                          .teacherId(teacherId)
                                                          .semesterId(semesterId)
                                                          .build();

        return getAllWithFilter(criteria);
    }

    @Override
    public List<UserDTO> getClassStudents(Long classId) {
        // Verify class exists
        getClassById(classId);

        // Get students with COMPLETED enrollment status
        List<User> students = enrollmentRepository.findUsersByClassAndStatus(classId, EnrollmentStatus.COMPLETED);

        return students.stream()
                       .map(userService::mapToDTO)
                       .toList();
    }

    @Override
    public List<ClassDTO> getStudentClasses(Long studentId, Long semesterId) {
        List<Class> classes = enrollmentRepository.findClassesByUserAndSemesterAndStatus(
                studentId, semesterId, EnrollmentStatus.COMPLETED);

        return classes.stream()
                      .map(this::mapToDTO)
                      .toList();
    }

    @Override
    public List<ClassDTO> getEnrolledClasses(Long semesterId) {
        Long userId = SecurityUtils.getCurrentUserId();
        List<Class> enrolledClasses = enrollmentRepository.findEnrolledClasses(semesterId, userId);

        return enrolledClasses.stream()
                              .map(this::mapToDTO)
                              .toList();
    }

    @Override
    @Transactional
    public ClassDTO updateClass(Long id, ClassRequestDTO.CreateClassRequest request) {
        // Find existing class
        Class existingClass = getClassById(id);

        // Get related entities
        Subject subject = subjectService.getSubjectById(request.getSubjectId());
        Semester semester = semesterService.getSemesterById(request.getSemesterId());
        User teacher = userService.getUserByIdOrThrow(request.getTeacherId());

        // Validate business rules
        validateClassRequest(request, id);

        // Validate class code uniqueness in semester (exclude current class)
        if (classRepository.existsByClassCodeAndSemesterIdAndIdNot(
                request.getClassCode(), request.getSemesterId(), id)) {
            throw new BadRequestException(CLASS_CODE_EXISTED_ERROR);
        }

        // Update class fields
        existingClass.setClassCode(request.getClassCode());
        existingClass.setSubject(subject);
        existingClass.setSemester(semester);
        existingClass.setTeacher(teacher);
        existingClass.setMaxStudents(request.getMaxStudents());
        existingClass.setMinStudents(request.getMinStudents());
        existingClass.setProcessPercent(request.getProcessPercent());
        existingClass.setMidtermPercent(request.getMidtermPercent());
        existingClass.setFinalPercent(request.getFinalPercent());

        // Clear existing schedules and add new ones
        existingClass.getSchedules().clear();
        List<ClassSchedule> schedules = request.getSchedules().stream()
                                               .map(scheduleRequest -> ClassSchedule.builder()
                                                                                    .clazz(existingClass)
                                                                                    .dayOfWeek(DayOfWeek.fromValue(scheduleRequest.getDayOfWeek()))
                                                                                    .startPeriod(scheduleRequest.getStartPeriod())
                                                                                    .endPeriod(scheduleRequest.getEndPeriod())
                                                                                    .build())
                                               .toList();

        existingClass.getSchedules().addAll(schedules);

        // Save and return DTO
        Class updatedClass = classRepository.save(existingClass);
        return mapToDTO(updatedClass);
    }

    @Override
    public List<ClassDTO> getAvailableClassesForRegistration(Long semesterId) {
        Long userId = SecurityUtils.getCurrentUserId();

        List<Class> availableClasses = classRepository.findAvailableClassesForRegistration(
                ClassStatus.OPEN_FOR_REGISTRATION,
                SemesterStatus.REGISTRATION_OPEN,
                semesterId,
                userId
        );

        return availableClasses.stream()
                               .map(this::mapToDTO)
                               .toList();
    }

    @Override
    public Class getClassById(Long id) {
        return classRepository.findById(id)
                              .orElseThrow(() -> new BadRequestException(CLASS_NOT_FOUND_ERROR));
    }

    @Override
    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public ClassDTO mapToDTO(Class clazz) {
        List<ClassScheduleDTO> schedules = clazz.getSchedules().stream()
                                                .map(schedule -> ClassScheduleDTO.builder()
                                                               .id(schedule.getId())
                                                               .dayOfWeek(schedule.getDayOfWeek().getDisplayName())
                                                               .dayOfWeekValue(schedule.getDayOfWeek().getValue())
                                                               .startPeriod(schedule.getStartPeriod())
                                                               .endPeriod(schedule.getEndPeriod())
                                                               .timeSlotDisplay(schedule.getTimeSlotDisplay())
                                                               .build())
                                                .toList();

        long enrolledCount = enrollmentRepository.countEnrolledStudents(clazz.getId(), EnrollmentStatus.ENROLLED);

        return ClassDTO.builder()
                       .id(clazz.getId())
                       .classCode(clazz.getClassCode())
                       .subject(subjectService.mapToDTO(clazz.getSubject()))
                       .semester(semesterService.mapToDTO(clazz.getSemester()))
                       .teacher(userService.mapToDTO(clazz.getTeacher()))
                       .maxStudents(clazz.getMaxStudents())
                       .minStudents(clazz.getMinStudents())
                       .processPercent(clazz.getProcessPercent())
                       .midtermPercent(clazz.getMidtermPercent())
                       .finalPercent(clazz.getFinalPercent())
                       .status(clazz.getStatus())
                       .schedules(schedules)
                       .createdAt(clazz.getCreatedAt())
                       .enrolledCount(enrolledCount)
                       .build();
    }

    @Override
    public PaginationDTO<ClassDTO> getClassesWithFilter(ClassFilterCriteria criteria) {
        return getWithFilter(criteria);
    }

    @Override
    protected JpaSpecificationExecutor<Class> getRepository() {
        return classRepository;
    }

    @Override
    protected BaseSpecificationBuilder<Class, ClassFilterCriteria> getSpecificationBuilder() {
        return classSpecification;
    }

    @Override
    protected Function<Class, ClassDTO> getEntityToDtoMapper() {
        return this::mapToDTO;
    }

    /**
     * Validate class request business rules
     */
    private void validateClassRequest(ClassRequestDTO.CreateClassRequest request, Long excludeClassId) {
        // Validate percentage total = 100
        int totalPercent = request.getProcessPercent() + request.getMidtermPercent() + request.getFinalPercent();
        if (totalPercent != 100) {
            throw new BadRequestException(CLASS_PERCENT_TOTAL_INVALID_ERROR);
        }

        // Validate minStudents < maxStudents
        if (request.getMinStudents() >= request.getMaxStudents()) {
            throw new BadRequestException(CLASS_MIN_MAX_STUDENTS_INVALID_ERROR);
        }

        // Validate schedule periods
        for (ClassRequestDTO.ClassScheduleRequest schedule : request.getSchedules()) {
            if (schedule.getEndPeriod() <= schedule.getStartPeriod()) {
                throw new BadRequestException(CLASS_SCHEDULE_PERIOD_INVALID_ERROR);
            }
        }

        // Validate no duplicate days in schedules
        Set<Integer> dayOfWeeks = request.getSchedules().stream()
                                         .map(ClassRequestDTO.ClassScheduleRequest::getDayOfWeek)
                                         .collect(Collectors.toSet());

        if (dayOfWeeks.size() != request.getSchedules().size()) {
            throw new BadRequestException(CLASS_SCHEDULE_DUPLICATE_DAY_ERROR);
        }
    }
}