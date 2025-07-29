package com.backend.quan_ly_hoc_vu_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleDTO {

    private Long id;
    private Integer dayOfWeek; // 1=Thứ 2, 2=Thứ 3, ..., 7=Chủ nhật
    private Integer startPeriod; // 1-10
    private Integer endPeriod; // 1-10

}