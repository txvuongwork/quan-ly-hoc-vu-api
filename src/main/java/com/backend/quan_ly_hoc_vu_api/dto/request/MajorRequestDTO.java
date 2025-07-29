package com.backend.quan_ly_hoc_vu_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class MajorRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMajorRequest {

        @NotBlank(message = MAJOR_CODE_REQUIRED_ERROR)
        private String majorCode;

        @NotBlank(message = MAJOR_NAME_REQUIRED_ERROR)
        private String majorName;

        private String description;

    }

}