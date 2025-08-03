package com.backend.quan_ly_hoc_vu_api.controller;

import com.backend.quan_ly_hoc_vu_api.dto.*;
import com.backend.quan_ly_hoc_vu_api.dto.request.AttendanceRequestDTO.*;
import com.backend.quan_ly_hoc_vu_api.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AttendanceController {

    AttendanceService attendanceService;

    /**
     * Teacher creates attendance session for a class
     */
    @PostMapping("/classes/{classId}/attendance-sessions")
    public ResponseEntity<AttendanceSessionDTO> createAttendanceSession(
            @PathVariable Long classId,
            @RequestBody @Valid CreateAttendanceSessionRequest request) {
        AttendanceSessionDTO session = attendanceService.createAttendanceSession(classId, request);
        return ResponseEntity.ok(session);
    }

    /**
     * Get attendance sessions for a class
     */
    @GetMapping("/classes/{classId}/attendance-sessions")
    public ResponseEntity<List<SimpleAttendanceSessionDTO>> getAttendanceSessionsByClass(@PathVariable Long classId) {
        List<SimpleAttendanceSessionDTO> sessions = attendanceService.getAttendanceSessionsByClass(classId);
        return ResponseEntity.ok(sessions);
    }

    /**
     * Update attendance session (activate/deactivate)
     */
    @PutMapping("/attendance-sessions/{sessionId}")
    public ResponseEntity<AttendanceSessionDTO> updateAttendanceSession(
            @PathVariable Long sessionId,
            @RequestBody @Valid UpdateAttendanceSessionRequest request) {
        AttendanceSessionDTO session = attendanceService.updateAttendanceSession(sessionId, request);
        return ResponseEntity.ok(session);
    }

    /**
     * Get attendance details for a session
     */
    @GetMapping("/attendance-sessions/{sessionId}/attendances")
    public ResponseEntity<List<AttendanceDTO>> getAttendancesBySession(@PathVariable Long sessionId) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesBySession(sessionId);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Student checks in using attendance code
     */
    @PostMapping("/attendance/check-in")
    public ResponseEntity<AttendanceDTO> checkIn(@RequestBody @Valid CheckInRequest request) {
        AttendanceDTO attendance = attendanceService.checkIn(request);
        return ResponseEntity.ok(attendance);
    }

}