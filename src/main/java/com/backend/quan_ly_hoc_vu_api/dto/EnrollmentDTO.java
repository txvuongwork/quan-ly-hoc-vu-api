package com.backend.quan_ly_hoc_vu_api.dto;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    private Long id;
    private UserDTO student;
    private ClassDTO clazz;
    private Instant enrollmentDate;
    private EnrollmentStatus status;
    private Instant createdAt;
    private Instant updatedAt;

}