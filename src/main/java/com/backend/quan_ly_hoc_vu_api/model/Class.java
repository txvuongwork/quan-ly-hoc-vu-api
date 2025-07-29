package com.backend.quan_ly_hoc_vu_api.model;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.ClassStatus;
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

    @Column(name = "class_code", nullable = false)
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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ClassSchedule> schedules = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public void addSchedule(ClassSchedule schedule) {
        schedules.add(schedule);
        schedule.setClassEntity(this);
    }

    public void removeSchedule(ClassSchedule schedule) {
        schedules.remove(schedule);
        schedule.setClassEntity(null);
    }

    public void clearSchedules() {
        schedules.forEach(schedule -> schedule.setClassEntity(null));
        schedules.clear();
    }

}
