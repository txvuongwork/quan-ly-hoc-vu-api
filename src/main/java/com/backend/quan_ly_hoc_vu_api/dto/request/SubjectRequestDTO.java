package com.backend.quan_ly_hoc_vu_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class SubjectRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSubjectRequest {

        @NotBlank(message = SUBJECT_CODE_REQUIRED_ERROR)
        private String subjectCode;

        @NotBlank(message = SUBJECT_NAME_REQUIRED_ERROR)
        private String subjectName;

        @NotBlank(message = SUBJECT_MAJOR_REQUIRED_ERROR)
        private Long majorId;

        @NotNull(message = SUBJECT_CREDITS_REQUIRED_ERROR)
        private Integer credits;

        private String description;

    }

}
