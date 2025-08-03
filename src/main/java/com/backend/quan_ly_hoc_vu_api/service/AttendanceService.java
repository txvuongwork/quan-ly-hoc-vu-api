package com.backend.quan_ly_hoc_vu_api.service;

import com.backend.quan_ly_hoc_vu_api.dto.*;
import com.backend.quan_ly_hoc_vu_api.dto.request.AttendanceRequestDTO;
import com.backend.quan_ly_hoc_vu_api.model.Attendance;
import com.backend.quan_ly_hoc_vu_api.model.AttendanceSession;

import java.util.List;

public interface AttendanceService {

    /**
     * Teacher creates attendance session for a class
     */
    AttendanceSessionDTO createAttendanceSession(Long classId, AttendanceRequestDTO.CreateAttendanceSessionRequest request);

    /**
     * Student checks in using attendance code
     */
    AttendanceDTO checkIn(AttendanceRequestDTO.CheckInRequest request);

    /**
     * Get attendance sessions for a class
     */
    List<SimpleAttendanceSessionDTO> getAttendanceSessionsByClass(Long classId);

    /**
     * Get attendance details for a session
     */
    List<AttendanceDTO> getAttendancesBySession(Long sessionId);

    /**
     * Update attendance session (activate/deactivate)
     */
    AttendanceSessionDTO updateAttendanceSession(Long sessionId, AttendanceRequestDTO.UpdateAttendanceSessionRequest request);

    /**
     * Get attendance session by id
     */
    AttendanceSession getAttendanceSessionById(Long sessionId);

    /**
     * Map AttendanceSession to DTO
     */
    AttendanceSessionDTO mapSessionToDTO(AttendanceSession session);

    /**
     * Map Attendance to DTO
     */
    AttendanceDTO mapAttendanceToDTO(Attendance attendance);

}