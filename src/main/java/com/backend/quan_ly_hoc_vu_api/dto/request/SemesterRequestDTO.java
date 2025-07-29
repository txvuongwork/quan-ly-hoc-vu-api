package com.backend.quan_ly_hoc_vu_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class SemesterRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSemesterRequest {

        @NotBlank(message = SEMESTER_NAME_REQUIRED_ERROR)
        private String semesterName;

        @NotNull(message = SEMESTER_YEAR_REQUIRED_ERROR)
        private Integer year;

        @NotNull(message = SEMESTER_NUMBER_REQUIRED_ERROR)
        private Integer semesterNumber;

        @NotNull(message = SEMESTER_START_REQUIRED_ERROR)
        private Instant semesterStart;

        @NotNull(message = SEMESTER_END_REQUIRED_ERROR)
        private Instant semesterEnd;

        @NotNull(message = REGISTRATION_START_REQUIRED_ERROR)
        private Instant registrationStart;

        @NotNull(message = REGISTRATION_END_REQUIRED_ERROR)
        private Instant registrationEnd;

    }

}