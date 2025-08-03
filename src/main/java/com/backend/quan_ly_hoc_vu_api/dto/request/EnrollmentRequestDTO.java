package com.backend.quan_ly_hoc_vu_api.dto.request;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class EnrollmentRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateEnrollmentRequest {

        @NotNull(message = ENROLLMENT_CLASS_REQUIRED_ERROR)
        private Long classId;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateEnrollmentStatusRequest {

        @NotNull(message = "error.validate.enrollment-status.required")
        private EnrollmentStatus status;

    }

}