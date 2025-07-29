package com.backend.quan_ly_hoc_vu_api.specification;

import com.backend.quan_ly_hoc_vu_api.dto.criteria.MajorFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Major;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MajorSpecification extends BaseSpecificationBuilder<Major, MajorFilterCriteria> {

    @Override
    protected void addSpecificPredicates(List<Predicate> predicates, MajorFilterCriteria criteria,
                                         Root<Major> root, CriteriaBuilder criteriaBuilder) {

        if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("majorName"), criteria.getName()));
        }

        if (criteria.getCode() != null && !criteria.getCode().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("majorCode"), criteria.getCode()));
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Major> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("majorName"), keyword));
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("majorCode"), keyword));

        return searchPredicates;
    }

}