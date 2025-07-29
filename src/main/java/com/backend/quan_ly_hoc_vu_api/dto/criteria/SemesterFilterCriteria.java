package com.backend.quan_ly_hoc_vu_api.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SemesterFilterCriteria extends BaseFilterCriteria {

    private String name;
    private Integer year;
    private Integer semesterNumber;

    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "year";
    }

}