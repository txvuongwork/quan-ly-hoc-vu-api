package com.backend.quan_ly_hoc_vu_api.specification;

import com.backend.quan_ly_hoc_vu_api.dto.criteria.SubjectFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Subject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectSpecification extends BaseSpecificationBuilder<Subject, SubjectFilterCriteria> {

    @Override
    protected void addSpecificPredicates(List<Predicate> predicates, SubjectFilterCriteria criteria,
                                         Root<Subject> root, CriteriaBuilder criteriaBuilder) {

        if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("subjectName"), criteria.getName()));
        }

        if (criteria.getCode() != null && !criteria.getCode().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("subjectCode"), criteria.getCode()));
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Subject> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("name"), keyword));

        return searchPredicates;
    }

}
