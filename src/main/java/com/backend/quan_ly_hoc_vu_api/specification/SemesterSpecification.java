package com.backend.quan_ly_hoc_vu_api.specification;

import com.backend.quan_ly_hoc_vu_api.dto.criteria.SemesterFilterCriteria;
import com.backend.quan_ly_hoc_vu_api.model.Semester;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SemesterSpecification extends BaseSpecificationBuilder<Semester, SemesterFilterCriteria> {

    @Override
    protected void addSpecificPredicates(List<Predicate> predicates, SemesterFilterCriteria criteria,
                                         Root<Semester> root, CriteriaBuilder criteriaBuilder) {

        // Filter by semester code
        if (criteria.getSemesterCode() != null && !criteria.getSemesterCode().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("semesterCode"), criteria.getSemesterCode()));
        }

        // Filter by status
        if (criteria.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }

        // Filter by start date range
        if (criteria.getStartDateFrom() != null) {
            try {
                LocalDate fromDate = LocalDate.parse(criteria.getStartDateFrom(), DATE_FORMATTER);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), fromDate));
            } catch (Exception e) {
                // Log error or handle invalid date format
            }
        }

        if (criteria.getStartDateTo() != null) {
            try {
                LocalDate toDate = LocalDate.parse(criteria.getStartDateTo(), DATE_FORMATTER);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), toDate));
            } catch (Exception e) {
                // Log error or handle invalid date format
            }
        }

        // Filter by end date range
        if (criteria.getEndDateFrom() != null) {
            try {
                LocalDate fromDate = LocalDate.parse(criteria.getEndDateFrom(), DATE_FORMATTER);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), fromDate));
            } catch (Exception e) {
                // Log error or handle invalid date format
            }
        }

        if (criteria.getEndDateTo() != null) {
            try {
                LocalDate toDate = LocalDate.parse(criteria.getEndDateTo(), DATE_FORMATTER);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), toDate));
            } catch (Exception e) {
                // Log error or handle invalid date format
            }
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Semester> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        // Search in semester code
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("semesterCode"), keyword));

        // Search in semester name
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("semesterName"), keyword));

        return searchPredicates;
    }

}