package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.ClassScheduleDTO;
import com.backend.quan_ly_hoc_vu_api.dto.common.PaginationDTO;
import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.dto.request.ClassRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.model.ClassSchedule;
import com.backend.quan_ly_hoc_vu_api.model.Subject;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import com.backend.quan_ly_hoc_vu_api.model.User;
import com.backend.quan_ly_hoc_vu_api.repository.ClassRepository;
import com.backend.quan_ly_hoc_vu_api.service.BaseFilterService;
import com.backend.quan_ly_hoc_vu_api.service.ClassService;
import com.backend.quan_ly_hoc_vu_api.service.SubjectService;
import com.backend.quan_ly_hoc_vu_api.service.SemesterService;
import com.backend.quan_ly_hoc_vu_api.service.UserService;
import com.backend.quan_ly_hoc_vu_api.specification.BaseSpecificationBuilder;
import com.backend.quan_ly_hoc_vu_api.specification.ClassSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClassServiceImpl extends BaseFilterService<Class, Long, ClassFilterCriteria, ClassDTO>
        implements ClassService {

    ClassRepository classRepository;
    ClassSpecification classSpecification;
    SubjectService subjectService;
    SemesterService semesterService;
    UserService userService;

    @Override
    @Transactional
    public ClassDTO createClass(ClassRequestDTO.CreateClassRequest request) {
        validateCreateClassRequest(request);

        if (classRepository.existsByClassCode(request.getClassCode())) {
            throw new BadRequestException(CLASS_CODE_EXISTED_ERROR);
        }

        Subject subject = subjectService.getSubjectById(request.getSubjectId());
        Semester semester = semesterService.getSemesterById(request.getSemesterId());
        User teacher = userService.getUserByIdOrThrow(request.getTeacherId());

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
                           .status(ClassStatus.OPEN)
                           .build();

        // Add schedules
        addSchedulesToClass(clazz, request.getSchedules());

        Class savedClass = classRepository.save(clazz);

        return mapToDTO(savedClass);
    }

    @Override
    @Transactional
    public ClassDTO updateClass(Long id, ClassRequestDTO.CreateClassRequest request) {
        Class existingClass = getClassById(id);
        validateCreateClassRequest(request);

        if (!existingClass.getClassCode().equals(request.getClassCode()) &&
            classRepository.existsByClassCode(request.getClassCode())) {
            throw new BadRequestException(CLASS_CODE_EXISTED_ERROR);
        }

        Subject subject = subjectService.getSubjectById(request.getSubjectId());
        Semester semester = semesterService.getSemesterById(request.getSemesterId());
        User teacher = userService.getUserByIdOrThrow(request.getTeacherId());

        existingClass.setClassCode(request.getClassCode());
        existingClass.setSubject(subject);
        existingClass.setSemester(semester);
        existingClass.setTeacher(teacher);
        existingClass.setMaxStudents(request.getMaxStudents());
        existingClass.setMinStudents(request.getMinStudents());
        existingClass.setProcessPercent(request.getProcessPercent());
        existingClass.setMidtermPercent(request.getMidtermPercent());
        existingClass.setFinalPercent(request.getFinalPercent());
        existingClass.setStatus(request.getStatus());

        // Clear old schedules and add new ones
        existingClass.clearSchedules();
        addSchedulesToClass(existingClass, request.getSchedules());

        Class updatedClass = classRepository.save(existingClass);

        return mapToDTO(updatedClass);
    }

    @Override
    @Transactional
    public void deleteClass(Long id) {
        Class clazz = getClassById(id);
        classRepository.delete(clazz);
    }

    @Override
    public Class getClassById(Long id) {
        return classRepository.findById(id)
                              .orElseThrow(() -> new BadRequestException(CLASS_NOT_FOUND_ERROR));
    }

    @Override
    public ClassDTO mapToDTO(Class clazz) {
        List<ClassScheduleDTO> scheduleDTOs = clazz.getSchedules().stream()
                                                   .map(this::mapScheduleToDTO)
                                                   .collect(Collectors.toList());

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
                       .createdAt(clazz.getCreatedAt())
                       .schedules(scheduleDTOs)
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

    // Private helper methods

    private void validateCreateClassRequest(ClassRequestDTO.CreateClassRequest request) {
        // Validate percentage total
        int totalPercent = request.getProcessPercent() + request.getMidtermPercent() + request.getFinalPercent();
        if (totalPercent != 100) {
            throw new BadRequestException(CLASS_PERCENT_TOTAL_INVALID_ERROR);
        }

        // Validate min <= max students
        if (request.getMinStudents() > request.getMaxStudents()) {
            throw new BadRequestException(CLASS_MIN_MAX_STUDENTS_INVALID_ERROR);
        }

        // Validate schedules
        validateSchedules(request.getSchedules());
    }

    private void validateSchedules(List<ClassRequestDTO.ClassScheduleRequest> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            throw new BadRequestException(CLASS_SCHEDULES_REQUIRED_ERROR);
        }

        Set<Integer> usedDays = new HashSet<>();

        for (ClassRequestDTO.ClassScheduleRequest schedule : schedules) {
            // Validate start period <= end period
            if (schedule.getStartPeriod() > schedule.getEndPeriod()) {
                throw new BadRequestException(CLASS_SCHEDULE_PERIOD_INVALID_ERROR);
            }

            // Validate no duplicate days
            if (usedDays.contains(schedule.getDayOfWeek())) {
                throw new BadRequestException(CLASS_SCHEDULE_DUPLICATE_DAY_ERROR);
            }
            usedDays.add(schedule.getDayOfWeek());
        }
    }

    private void addSchedulesToClass(Class clazz, List<ClassRequestDTO.ClassScheduleRequest> scheduleRequests) {
        for (ClassRequestDTO.ClassScheduleRequest scheduleRequest : scheduleRequests) {
            ClassSchedule schedule = ClassSchedule.builder()
                                                  .dayOfWeek(scheduleRequest.getDayOfWeek())
                                                  .startPeriod(scheduleRequest.getStartPeriod())
                                                  .endPeriod(scheduleRequest.getEndPeriod())
                                                  .build();

            clazz.addSchedule(schedule);
        }
    }

    private ClassScheduleDTO mapScheduleToDTO(ClassSchedule schedule) {
        return ClassScheduleDTO.builder()
                               .id(schedule.getId())
                               .dayOfWeek(schedule.getDayOfWeek())
                               .startPeriod(schedule.getStartPeriod())
                               .endPeriod(schedule.getEndPeriod())
                               .build();
    }

}