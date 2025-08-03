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

        // Filter by class code
        if (criteria.getClassCode() != null && !criteria.getClassCode().trim().isEmpty()) {
            predicates.add(likeIgnoreCase(criteriaBuilder, root.get("classCode"), criteria.getClassCode()));
        }

        // Filter by subject ID
        if (criteria.getSubjectId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subject").get("id"), criteria.getSubjectId()));
        }

        // Filter by semester ID
        if (criteria.getSemesterId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("semester").get("id"), criteria.getSemesterId()));
        }

        // Filter by teacher ID
        if (criteria.getTeacherId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("teacher").get("id"), criteria.getTeacherId()));
        }

        // Filter by status
        if (criteria.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }

        // Filter by min students range
        if (criteria.getMinStudentsFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minStudents"), criteria.getMinStudentsFrom()));
        }

        if (criteria.getMinStudentsTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minStudents"), criteria.getMinStudentsTo()));
        }

        // Filter by max students range
        if (criteria.getMaxStudentsFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxStudents"), criteria.getMaxStudentsFrom()));
        }

        if (criteria.getMaxStudentsTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxStudents"), criteria.getMaxStudentsTo()));
        }
    }

    @Override
    protected List<Predicate> buildSearchPredicates(String keyword, Root<Class> root,
                                                    CriteriaBuilder criteriaBuilder) {
        List<Predicate> searchPredicates = new ArrayList<>();

        // Search in class code
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("classCode"), keyword));

        // Search in subject name
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("subject").get("subjectName"), keyword));

        // Search in subject code
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("subject").get("subjectCode"), keyword));

        // Search in teacher name
        searchPredicates.add(likeIgnoreCase(criteriaBuilder, root.get("teacher").get("fullName"), keyword));

        return searchPredicates;
    }
}