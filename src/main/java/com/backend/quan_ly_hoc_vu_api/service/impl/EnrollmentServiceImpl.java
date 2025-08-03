package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.dto.ClassDTO;
import com.backend.quan_ly_hoc_vu_api.dto.UserDTO;
import com.backend.quan_ly_hoc_vu_api.service.ClassService;
import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.EnrollmentDTO;
import com.backend.quan_ly_hoc_vu_api.dto.request.EnrollmentRequestDTO;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.*;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.repository.EnrollmentRepository;
import com.backend.quan_ly_hoc_vu_api.repository.SemesterRepository;
import com.backend.quan_ly_hoc_vu_api.service.EnrollmentService;
import com.backend.quan_ly_hoc_vu_api.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final int MAX_CREDITS_PER_SEMESTER = 25;

    EnrollmentRepository enrollmentRepository;
    SemesterRepository semesterRepository;
    ClassService classService;
    UserService userService;

    @Override
    @Transactional
    public EnrollmentDTO registerForClass(EnrollmentRequestDTO.CreateEnrollmentRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BadRequestException(USER_NOT_AUTHENTICATED_ERROR);
        }

        User currentUser = userService.getUserByIdOrThrow(currentUserId);
        Class targetClass = classService.getClassById(request.getClassId());


        validateRegistrationRequirements(currentUser, targetClass);

        Enrollment enrollment = Enrollment.builder()
                                          .student(currentUser)
                                          .clazz(targetClass)
                                          .status(EnrollmentStatus.ENROLLED)
                                          .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return mapToDTO(savedEnrollment);
    }
//
//    @Override
//    @Transactional
//    public void cancelEnrollment(Long enrollmentId) {
//        // Get current user
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        if (currentUserId == null) {
//            throw new BadRequestException(USER_NOT_AUTHENTICATED_ERROR);
//        }
//
//        Enrollment enrollment = getEnrollmentById(enrollmentId);
//
//        // Check if enrollment belongs to current user
//        if (!enrollment.getUser().getId().equals(currentUserId)) {
//            throw new BadRequestException(ENROLLMENT_NOT_FOUND_ERROR);
//        }
//
//        // Check if cancellation is allowed (within registration period)
//        Class enrolledClass = enrollment.getClassEntity();
//        Semester semester = enrolledClass.getSemester();
//        Instant now = Instant.now();
//
//        if (now.isBefore(semester.getRegistrationStart()) || now.isAfter(semester.getRegistrationEnd())) {
//            throw new BadRequestException(ENROLLMENT_CANNOT_CANCEL_OUTSIDE_REGISTRATION_PERIOD_ERROR);
//        }
//
//        // Delete enrollment
//        enrollmentRepository.delete(enrollment);
//    }
//
//    @Override
//    public List<EnrollmentDTO> getMyCurrentEnrollments() {
//        // Get current user
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        if (currentUserId == null) {
//            throw new BadRequestException(USER_NOT_AUTHENTICATED_ERROR);
//        }
//
//        // Find current registration semester
//        Instant now = Instant.now();
//        Optional<Semester> currentSemesterOpt = semesterRepository
//                .findFirstByRegistrationStartLessThanEqualAndRegistrationEndGreaterThanEqualOrderByRegistrationStartDesc(now, now);
//
//        if (currentSemesterOpt.isEmpty()) {
//            // No active registration period, throw error for consistency
//            throw new BadRequestException(ENROLLMENT_NO_ACTIVE_REGISTRATION_PERIOD_ERROR);
//        }
//
//        Semester currentSemester = currentSemesterOpt.get();
//
//        // Get enrollments for current user in current semester
//        List<Enrollment> enrollments = enrollmentRepository.findStudentEnrollmentsInSemester(
//                currentUserId, currentSemester.getId(), EnrollmentStatus.ENROLLED);
//
//        return enrollments.stream()
//                          .map(this::mapToDTO)
//                          .collect(Collectors.toList());
//    }
//
//    @Override
//    public Enrollment getEnrollmentById(Long enrollmentId) {
//        return enrollmentRepository.findById(enrollmentId)
//                                   .orElseThrow(() -> new BadRequestException(ENROLLMENT_NOT_FOUND_ERROR));
//    }

    @Override
    public EnrollmentDTO mapToDTO(Enrollment enrollment) {
        return EnrollmentDTO.builder()
                            .id(enrollment.getId())
                            .student(userService.mapToDTO(enrollment.getStudent()))
                            .clazz(classService.mapToDTO(enrollment.getClazz()))
                            .enrollmentDate(enrollment.getEnrollmentDate())
                            .status(enrollment.getStatus())
                            .createdAt(enrollment.getCreatedAt())
                            .build();
    }

//    @Override
//    public List<ClassDTO> getClassesByStatus(Long semesterId, EnrollmentStatus status) {
//        // Get current user
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        if (currentUserId == null) {
//            throw new BadRequestException(USER_NOT_AUTHENTICATED_ERROR);
//        }
//
//        // Find classes for user by semester and status
//        List<Class> classes = enrollmentRepository.findClassesByUserAndSemesterAndStatus(
//                currentUserId, semesterId, status);
//
//        // Convert to ClassDTOs
//        return classes.stream()
//                      .map(classService::mapToDTO)
//                      .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserDTO> getStudentsByClassAndStatus(Long classId, EnrollmentStatus status) {
//        // Find users by class and status
//        List<User> users = enrollmentRepository.findUsersByClassAndStatus(classId, status);
//
//        // Convert to UserDTOs
//        return users.stream()
//                    .map(userService::mapToDTO)
//                    .collect(Collectors.toList());
//    }
//
    // Private helper methods

    private void validateRegistrationRequirements(User user, Class targetClass) {
        validateClassAvailability(targetClass);

        validateNotAlreadyEnrolled(user.getId(), targetClass.getId());

        validateClassNotFull(targetClass);

        validateNoScheduleConflict(user.getId(), targetClass);

        validateCreditLimit(user.getId(), targetClass);
    }

    private void validateClassAvailability(Class targetClass) {
        if (targetClass.getStatus() != ClassStatus.OPEN_FOR_REGISTRATION) {
            throw new BadRequestException(ENROLLMENT_CLASS_NOT_AVAILABLE_ERROR);
        }
    }

    private void validateNotAlreadyEnrolled(Long userId, Long classId) {
        if (enrollmentRepository.isStudentEnrolledInClass(userId, classId, EnrollmentStatus.ENROLLED)) {
            throw new BadRequestException(ENROLLMENT_ALREADY_ENROLLED_ERROR);
        }
    }

    private void validateClassNotFull(Class targetClass) {
        long enrolledCount = enrollmentRepository.countEnrolledStudents(targetClass.getId(), EnrollmentStatus.ENROLLED);
        if (enrolledCount >= targetClass.getMaxStudents()) {
            throw new BadRequestException(ENROLLMENT_CLASS_FULL_ERROR);
        }
    }

    private void validateNoScheduleConflict(Long userId, Class targetClass) {
        // Get all classes the student is enrolled in for this semester
        List<Enrollment> existingEnrollments = enrollmentRepository.findStudentEnrollmentsInSemester(
                userId, targetClass.getSemester().getId(), EnrollmentStatus.ENROLLED);

        // Get schedules of target class
        List<ClassSchedule> targetSchedules = targetClass.getSchedules();

        // Check for conflicts
        for (Enrollment enrollment : existingEnrollments) {
            Class enrolledClass = enrollment.getClazz();
            List<ClassSchedule> enrolledSchedules = enrolledClass.getSchedules();

            for (ClassSchedule targetSchedule : targetSchedules) {
                for (ClassSchedule enrolledSchedule : enrolledSchedules) {
                    if (hasScheduleConflict(targetSchedule, enrolledSchedule)) {
                        throw new BadRequestException(ENROLLMENT_SCHEDULE_CONFLICT_ERROR);
                    }
                }
            }
        }
    }

    private boolean hasScheduleConflict(ClassSchedule schedule1, ClassSchedule schedule2) {
        // Same day of week
        if (!schedule1.getDayOfWeek().equals(schedule2.getDayOfWeek())) {
            return false;
        }

        // Check if periods overlap
        int start1 = schedule1.getStartPeriod();
        int end1 = schedule1.getEndPeriod();
        int start2 = schedule2.getStartPeriod();
        int end2 = schedule2.getEndPeriod();

        // Two ranges overlap if: start1 <= end2 && start2 <= end1
        return start1 <= end2 && start2 <= end1;
    }

    private void validateCreditLimit(Long userId, Class targetClass) {
        // Get current total credits enrolled in this semester
        int currentCredits = enrollmentRepository.getTotalCreditsEnrolledInSemester(
                userId, targetClass.getSemester().getId(), EnrollmentStatus.ENROLLED);

        // Add credits of the class to be enrolled
        int newTotalCredits = currentCredits + targetClass.getSubject().getCredits();

        if (newTotalCredits > MAX_CREDITS_PER_SEMESTER) {
            throw new BadRequestException(ENROLLMENT_CREDIT_LIMIT_EXCEEDED_ERROR);
        }
    }

}