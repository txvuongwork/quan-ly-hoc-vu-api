package com.backend.quan_ly_hoc_vu_api.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubjectFilterCriteria extends BaseFilterCriteria {

    private String name;
    private String code;

    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "name";
    }

}
