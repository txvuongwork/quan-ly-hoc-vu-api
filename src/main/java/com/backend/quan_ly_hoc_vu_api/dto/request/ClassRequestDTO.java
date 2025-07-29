package com.backend.quan_ly_hoc_vu_api.dto.request;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class ClassRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateClassRequest {

        @NotBlank(message = CLASS_CODE_REQUIRED_ERROR)
        private String classCode;

        @NotNull(message = CLASS_SUBJECT_REQUIRED_ERROR)
        private Long subjectId;

        @NotNull(message = CLASS_SEMESTER_REQUIRED_ERROR)
        private Long semesterId;

        @NotNull(message = CLASS_TEACHER_REQUIRED_ERROR)
        private Long teacherId;

        @NotNull(message = CLASS_MAX_STUDENTS_REQUIRED_ERROR)
        @Min(value = 1, message = CLASS_MAX_STUDENTS_REQUIRED_ERROR)
        private Integer maxStudents;

        @NotNull(message = CLASS_MIN_STUDENTS_REQUIRED_ERROR)
        @Min(value = 1, message = CLASS_MIN_STUDENTS_REQUIRED_ERROR)
        private Integer minStudents;

        @NotNull(message = CLASS_PROCESS_PERCENT_REQUIRED_ERROR)
        @Min(value = 0, message = CLASS_PROCESS_PERCENT_REQUIRED_ERROR)
        @Max(value = 100, message = CLASS_PROCESS_PERCENT_REQUIRED_ERROR)
        private Integer processPercent;

        @NotNull(message = CLASS_MIDTERM_PERCENT_REQUIRED_ERROR)
        @Min(value = 0, message = CLASS_MIDTERM_PERCENT_REQUIRED_ERROR)
        @Max(value = 100, message = CLASS_MIDTERM_PERCENT_REQUIRED_ERROR)
        private Integer midtermPercent;

        @NotNull(message = CLASS_FINAL_PERCENT_REQUIRED_ERROR)
        @Min(value = 0, message = CLASS_FINAL_PERCENT_REQUIRED_ERROR)
        @Max(value = 100, message = CLASS_FINAL_PERCENT_REQUIRED_ERROR)
        private Integer finalPercent;

        @NotNull(message = CLASS_STATUS_REQUIRED_ERROR)
        private ClassStatus status;

        @NotNull(message = CLASS_SCHEDULES_REQUIRED_ERROR)
        @NotEmpty(message = CLASS_SCHEDULES_REQUIRED_ERROR)
        @Valid
        private List<ClassScheduleRequest> schedules;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassScheduleRequest {

        @NotNull(message = CLASS_SCHEDULE_DAY_REQUIRED_ERROR)
        @Min(value = 1, message = CLASS_SCHEDULE_DAY_INVALID_ERROR)
        @Max(value = 7, message = CLASS_SCHEDULE_DAY_INVALID_ERROR)
        private Integer dayOfWeek; // 1=Thứ 2, 2=Thứ 3, ..., 7=Chủ nhật

        @NotNull(message = CLASS_SCHEDULE_START_PERIOD_REQUIRED_ERROR)
        @Min(value = 1, message = CLASS_SCHEDULE_PERIOD_INVALID_ERROR)
        @Max(value = 10, message = CLASS_SCHEDULE_PERIOD_INVALID_ERROR)
        private Integer startPeriod;

        @NotNull(message = CLASS_SCHEDULE_END_PERIOD_REQUIRED_ERROR)
        @Min(value = 1, message = CLASS_SCHEDULE_PERIOD_INVALID_ERROR)
        @Max(value = 10, message = CLASS_SCHEDULE_PERIOD_INVALID_ERROR)
        private Integer endPeriod;

    }

}