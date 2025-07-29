package com.backend.quan_ly_hoc_vu_api.specification;

import com.backend.quan_ly_hoc_vu_api.dto.criteria.ClassFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Class;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassSpecification extends BaseSpecificationBuilder<Class, ClassFilterCriteria> {

    @Override
    protected void addSpecificPredicates(List<Predicate> predicates, ClassFilterCriteria criteria,
                                         Root<Class> root, CriteriaBuilder criteriaBuilder) {

        if (criteria.getClassCode() != null && !criteria.getClassCode().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("classCode"), criteria.getClassCode()));
        }

        if (criteria.getSubjectId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subject").get("id"), criteria.getSubjectId()));
        }

        if (criteria.getSemesterId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("semester").get("id"), criteria.getSemesterId()));
        }

        if (criteria.getTeacherId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("teacher").get("id"), criteria.getTeacherId()));
        }

        if (criteria.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Class> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("classCode"), keyword));
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("subject").get("subjectName"), keyword));
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("subject").get("subjectCode"), keyword));

        return searchPredicates;
    }

}