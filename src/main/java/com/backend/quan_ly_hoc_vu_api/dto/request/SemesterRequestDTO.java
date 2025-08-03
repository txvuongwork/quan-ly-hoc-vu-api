package com.backend.quan_ly_hoc_vu_api.dto.request;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class SemesterRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSemesterRequest {

        @NotBlank(message = SEMESTER_CODE_REQUIRED_ERROR)
        private String semesterCode;

        @NotBlank(message = SEMESTER_NAME_REQUIRED_ERROR)
        private String semesterName;

        @NotNull(message = SEMESTER_START_DATE_REQUIRED_ERROR)
        private LocalDate startDate;

        @NotNull(message = SEMESTER_END_DATE_REQUIRED_ERROR)
        private LocalDate endDate;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateSemesterRequest {

        @NotBlank(message = SEMESTER_CODE_REQUIRED_ERROR)
        private String semesterCode;

        @NotBlank(message = SEMESTER_NAME_REQUIRED_ERROR)
        private String semesterName;

        @NotNull(message = SEMESTER_START_DATE_REQUIRED_ERROR)
        private LocalDate startDate;

        @NotNull(message = SEMESTER_END_DATE_REQUIRED_ERROR)
        private LocalDate endDate;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateSemesterStatusRequest {

        @NotNull(message = SEMESTER_STATUS_REQUIRED_ERROR)
        private SemesterStatus status;

    }

}