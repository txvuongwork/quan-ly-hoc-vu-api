package com.backend.quan_ly_hoc_vu_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSessionDTO {
    private Long id;
    private String attendanceCode;
    private ClassDTO classEntity;
    private UserDTO createdBy;
    private Boolean isActive;
    private Instant createdAt;
    private Long attendanceCount; // Number of students attended
}