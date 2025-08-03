package com.backend.quan_ly_hoc_vu_api.dto.criteria;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
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
public class ClassFilterCriteria extends BaseFilterCriteria {

    private String classCode;
    private Long subjectId;
    private Long semesterId;
    private Long teacherId;
    private ClassStatus status;
    private Integer minStudentsFrom;
    private Integer minStudentsTo;
    private Integer maxStudentsFrom;
    private Integer maxStudentsTo;

    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "classCode";
    }
}