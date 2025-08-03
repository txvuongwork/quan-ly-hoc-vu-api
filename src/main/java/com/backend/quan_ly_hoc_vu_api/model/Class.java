package com.backend.quan_ly_hoc_vu_api.model;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_classes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "class_code", nullable = false, length = 50)
    private String classCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(name = "max_students", nullable = false)
    private Integer maxStudents;

    @Column(name = "min_students", nullable = false)
    private Integer minStudents;

    @Column(name = "process_percent", nullable = false)
    private Integer processPercent;

    @Column(name = "midterm_percent", nullable = false)
    private Integer midtermPercent;

    @Column(name = "final_percent", nullable = false)
    private Integer finalPercent;

    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClassStatus status = ClassStatus.DRAFT;

    @Column(name = "created_at")
    private Instant createdAt;

    // Relationships
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<ClassSchedule> schedules = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    // Helper methods
    public boolean isDraft() {
        return ClassStatus.DRAFT.equals(this.status);
    }

    public boolean isOpenForRegistration() {
        return ClassStatus.OPEN_FOR_REGISTRATION.equals(this.status);
    }

    public boolean isConfirmed() {
        return ClassStatus.CONFIRMED.equals(this.status);
    }

    public boolean isCancelled() {
        return ClassStatus.CANCELLED.equals(this.status);
    }

    public boolean isInProgress() {
        return ClassStatus.IN_PROGRESS.equals(this.status);
    }

    public boolean isCompleted() {
        return ClassStatus.COMPLETED.equals(this.status);
    }

    public void addSchedule(ClassSchedule schedule) {
        schedule.setClazz(this);
        this.schedules.add(schedule);
    }

    public void removeSchedule(ClassSchedule schedule) {
        schedule.setClazz(null);
        this.schedules.remove(schedule);
    }
}