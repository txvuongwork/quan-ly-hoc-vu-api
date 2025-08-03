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
public class SimpleAttendanceSessionDTO {
    private Long id;
    private String attendanceCode;
    private Boolean isActive;
    private Instant createdAt;
    private Long attendanceCount;
}