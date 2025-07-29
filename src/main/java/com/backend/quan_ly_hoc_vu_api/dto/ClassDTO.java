package com.backend.quan_ly_hoc_vu_api.dto;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {

    private Long id;
    private String classCode;
    private SubjectDTO subject;
    private SemesterDTO semester;
    private UserDTO teacher;
    private Integer maxStudents;
    private Integer minStudents;
    private Integer processPercent;
    private Integer midtermPercent;
    private Integer finalPercent;
    private ClassStatus status;
    private Instant createdAt;

}