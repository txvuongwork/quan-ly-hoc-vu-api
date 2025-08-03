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
    private String dayOfWeek;
    private Integer dayOfWeekValue;
    private Integer startPeriod;
    private Integer endPeriod;
    private String timeSlotDisplay;
}