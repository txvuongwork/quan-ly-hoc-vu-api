package com.backend.quan_ly_hoc_vu_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AttendanceRequestDTO {

    @Data
    @NoArgsConstructor
    public static class CreateAttendanceSessionRequest {
        // Empty for now, classId will come from path variable
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckInRequest {
        private String attendanceCode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateAttendanceSessionRequest {
        private Boolean isActive;
    }
}