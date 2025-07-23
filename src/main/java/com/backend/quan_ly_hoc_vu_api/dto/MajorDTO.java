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
public class MajorDTO {

    private Long id;
    private String majorCode;
    private String majorName;
    private String description;
    private Instant createdAt;

}
