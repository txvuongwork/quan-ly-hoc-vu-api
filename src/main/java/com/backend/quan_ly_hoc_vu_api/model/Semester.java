package com.backend.quan_ly_hoc_vu_api.model;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.SemesterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "t_semesters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "semester_name", nullable = false)
    private String semesterName;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "semester_number", nullable = false)
    private Integer semesterNumber;

    @Column(name = "semester_start", nullable = false)
    private Instant semesterStart;

    @Column(name = "semester_end", nullable = false)
    private Instant semesterEnd;

    @Column(name = "registration_start", nullable = false)
    private Instant registrationStart;

    @Column(name = "registration_end", nullable = false)
    private Instant registrationEnd;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SemesterStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

}
