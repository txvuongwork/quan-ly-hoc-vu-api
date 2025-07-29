package com.backend.quan_ly_hoc_vu_api.specification;

import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SemesterSpecification extends BaseSpecificationBuilder<Semester, SemesterFilterCriteria> {

    @Override
    protected void addSpecificPredicates(List<Predicate> predicates, SemesterFilterCriteria criteria,
                                         Root<Semester> root, CriteriaBuilder criteriaBuilder) {

        if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("semesterName"), criteria.getName()));
        }

        if (criteria.getYear() != null) {
            predicates.add(criteriaBuilder.equal(root.get("year"), criteria.getYear()));
        }

        if (criteria.getSemesterNumber() != null) {
            predicates.add(criteriaBuilder.equal(root.get("semesterNumber"), criteria.getSemesterNumber()));
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Semester> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("semesterName"), keyword));

        // Add year search if keyword is numeric
        try {
            Integer yearKeyword = Integer.parseInt(keyword);
            searchPredicates.add(criteriaBuilder.equal(root.get("year"), yearKeyword));
        } catch (NumberFormatException e) {
            // Keyword is not numeric, skip year search
        }

        return searchPredicates;
    }

}