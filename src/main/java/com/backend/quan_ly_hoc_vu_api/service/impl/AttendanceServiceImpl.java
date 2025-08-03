package com.backend.quan_ly_hoc_vu_api.service.impl;

import com.backend.quan_ly_hoc_vu_api.config.jwt.SecurityUtils;
import com.backend.quan_ly_hoc_vu_api.dto.*;
import com.backend.quan_ly_hoc_vu_api.dto.request.AttendanceRequestDTO.*;
import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import com.backend.quan_ly_hoc_vu_api.helper.exception.BadRequestException;
import com.backend.quan_ly_hoc_vu_api.model.Attendance;
import com.backend.quan_ly_hoc_vu_api.model.AttendanceSession;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import com.backend.quan_ly_hoc_vu_api.model.User;
import com.backend.quan_ly_hoc_vu_api.repository.AttendanceRepository;
import com.backend.quan_ly_hoc_vu_api.repository.AttendanceSessionRepository;
import com.backend.quan_ly_hoc_vu_api.repository.EnrollmentRepository;
import com.backend.quan_ly_hoc_vu_api.service.AttendanceService;
import com.backend.quan_ly_hoc_vu_api.service.ClassService;
import com.backend.quan_ly_hoc_vu_api.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    AttendanceSessionRepository attendanceSessionRepository;
    AttendanceRepository attendanceRepository;
    EnrollmentRepository enrollmentRepository;
    ClassService classService;
    UserService userService;

    @Override
    @Transactional
    public AttendanceSessionDTO createAttendanceSession(Long classId, CreateAttendanceSessionRequest request) {
        // Get current user (teacher)
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BadRequestException(UNAUTHORIZED);
        }

        // Get class and validate
        Class clazz = classService.getClassById(classId);
        User teacher = userService.getUserByIdOrThrow(currentUserId);

        // Generate unique attendance code
        String attendanceCode = generateAttendanceCode();
        while (attendanceSessionRepository.existsByAttendanceCode(attendanceCode)) {
            attendanceCode = generateAttendanceCode();
        }

        // Create attendance session
        AttendanceSession session = AttendanceSession.builder()
                                                     .attendanceCode(attendanceCode)
                                                     .classEntity(clazz)
                                                     .createdBy(teacher)
                                                     .isActive(true)
                                                     .build();

        AttendanceSession savedSession = attendanceSessionRepository.save(session);

        return mapSessionToDTO(savedSession);
    }

    @Override
    @Transactional
    public AttendanceDTO checkIn(CheckInRequest request) {
        // Get current user (student)
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BadRequestException(UNAUTHORIZED);
        }

        User student = userService.getUserByIdOrThrow(currentUserId);

        // Find attendance session by code
        AttendanceSession session = attendanceSessionRepository.findByAttendanceCode(request.getAttendanceCode())
                                                               .orElseThrow(() -> new BadRequestException("Mã điểm danh không hợp lệ"));

        // Check if session is active
        if (!session.getIsActive()) {
            throw new BadRequestException("Buổi điểm danh đã kết thúc");
        }

        // Check if student is enrolled in this class
        boolean isEnrolled = enrollmentRepository.isStudentEnrolledInClass(
                currentUserId,
                session.getClassEntity().getId(),
                EnrollmentStatus.COMPLETED
        );
        if (!isEnrolled) {
            throw new BadRequestException("Bạn không được phép điểm danh lớp này");
        }

        // Check if student already attended
        if (attendanceRepository.isStudentAlreadyAttended(session.getId(), currentUserId)) {
            throw new BadRequestException("Bạn đã điểm danh buổi học này rồi");
        }

        // Create attendance record
        Attendance attendance = Attendance.builder()
                                          .attendanceSession(session)
                                          .student(student)
                                          .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return mapAttendanceToDTO(savedAttendance);
    }

    @Override
    public List<SimpleAttendanceSessionDTO> getAttendanceSessionsByClass(Long classId) {
        // Validate class exists
        classService.getClassById(classId);

        // Get attendance sessions
        List<AttendanceSession> sessions = attendanceSessionRepository.findByClassIdOrderByCreatedAtDesc(classId);

        // Convert to DTOs with attendance count
        return sessions.stream()
                       .map(session -> SimpleAttendanceSessionDTO.builder()
                                                                 .id(session.getId())
                                                                 .attendanceCode(session.getAttendanceCode())
                                                                 .isActive(session.getIsActive())
                                                                 .createdAt(session.getCreatedAt())
                                                                 .attendanceCount(attendanceRepository.countBySessionId(session.getId()))
                                                                 .build())
                       .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendancesBySession(Long sessionId) {
        // Validate session exists
        getAttendanceSessionById(sessionId);

        // Get attendances
        List<Attendance> attendances = attendanceRepository.findBySessionIdOrderByAttendedAt(sessionId);

        // Convert to DTOs
        return attendances.stream()
                          .map(this::mapAttendanceToDTO)
                          .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AttendanceSessionDTO updateAttendanceSession(Long sessionId, UpdateAttendanceSessionRequest request) {
        // Get current user
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BadRequestException(UNAUTHORIZED);
        }

        // Get session
        AttendanceSession session = getAttendanceSessionById(sessionId);

        // Update session
        session.setIsActive(request.getIsActive());
        AttendanceSession updatedSession = attendanceSessionRepository.save(session);

        return mapSessionToDTO(updatedSession);
    }

    @Override
    public AttendanceSession getAttendanceSessionById(Long sessionId) {
        return attendanceSessionRepository.findById(sessionId)
                                          .orElseThrow(() -> new BadRequestException("Không tìm thấy buổi điểm danh"));
    }

    @Override
    public AttendanceSessionDTO mapSessionToDTO(AttendanceSession session) {
        return AttendanceSessionDTO.builder()
                                   .id(session.getId())
                                   .attendanceCode(session.getAttendanceCode())
                                   .classEntity(classService.mapToDTO(session.getClassEntity()))
                                   .createdBy(userService.mapToDTO(session.getCreatedBy()))
                                   .isActive(session.getIsActive())
                                   .createdAt(session.getCreatedAt())
                                   .attendanceCount(attendanceRepository.countBySessionId(session.getId()))
                                   .build();
    }

    @Override
    public AttendanceDTO mapAttendanceToDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                            .id(attendance.getId())
                            .attendanceSession(AttendanceSessionDTO.builder()
                                                                 .id(attendance.getAttendanceSession().getId())
                                                                 .attendanceCode(attendance.getAttendanceSession().getAttendanceCode())
                                                                 .isActive(attendance.getAttendanceSession().getIsActive())
                                                                 .createdAt(attendance.getAttendanceSession().getCreatedAt())
                                                                 .build())
                            .student(userService.mapToDTO(attendance.getStudent()))
                            .attendedAt(attendance.getAttendedAt())
                            .createdAt(attendance.getCreatedAt())
                            .build();
    }

    // Private helper methods

    private String generateAttendanceCode() {
        // Generate 6-digit random code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

}