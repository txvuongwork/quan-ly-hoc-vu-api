package com.backend.quan_ly_hoc_vu_api.dto;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {

    private Long id;
    private String semesterName;
    private Integer year;
    private Integer semesterNumber;
    private Instant semesterStart;
    private Instant semesterEnd;
    private Instant registrationStart;
    private Instant registrationEnd;
    private SemesterStatus status;
    private Instant createdAt;

}