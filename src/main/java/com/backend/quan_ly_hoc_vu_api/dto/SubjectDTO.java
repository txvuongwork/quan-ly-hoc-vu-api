package com.backend.quan_ly_hoc_vu_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private Long id;
    private String subjectCode;
    private String subjectName;
    private Integer credits;
    private String description;
    private MajorDTO major;
    private Instant createdAt;

}
