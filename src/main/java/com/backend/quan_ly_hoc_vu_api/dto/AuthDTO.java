package com.backend.quan_ly_hoc_vu_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.quan_ly_hoc_vu_api.helper.constant.Message.*;

public class AuthDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = EMAIL_REQUIRED_ERROR)
        @Email(message = EMAIL_FORMAT_ERROR)
        private String email;

        @NotBlank(message = PASSWORD_REQUIRED_ERROR)
        @Size(min = 6, message = PASSWORD_MIN_LENGTH_ERROR)
        private String password;

        @NotBlank(message = FULL_NAME_REQUIRED_ERROR)
        private String fullName;

        private String studentId;

        @NotBlank(message = UNIVERSITY_REQUIRED_ERROR)
        private Long universityId;
        private String major;
        private Integer year;
        private String phone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = EMAIL_REQUIRED_ERROR)
        @Email(message = EMAIL_FORMAT_ERROR)
        private String email;

        @NotBlank(message = PASSWORD_REQUIRED_ERROR)
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String tokenType;
        private UserDTO user;
        private String message;
    }

}