package com.backend.quan_ly_hoc_vu_api.model;

import com.backend.quan_ly_hoc_vu_api.helper.enumeration.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_class_schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Class clazz;

    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_period", nullable = false)
    private Integer startPeriod;

    @Column(name = "end_period", nullable = false)
    private Integer endPeriod;

    // Helper methods
    public boolean isOnMonday() {
        return DayOfWeek.MONDAY.equals(this.dayOfWeek);
    }

    public boolean isOnTuesday() {
        return DayOfWeek.TUESDAY.equals(this.dayOfWeek);
    }

    public boolean isOnWednesday() {
        return DayOfWeek.WEDNESDAY.equals(this.dayOfWeek);
    }

    public boolean isOnThursday() {
        return DayOfWeek.THURSDAY.equals(this.dayOfWeek);
    }

    public boolean isOnFriday() {
        return DayOfWeek.FRIDAY.equals(this.dayOfWeek);
    }

    public boolean isOnSaturday() {
        return DayOfWeek.SATURDAY.equals(this.dayOfWeek);
    }

    public boolean isOnSunday() {
        return DayOfWeek.SUNDAY.equals(this.dayOfWeek);
    }

    public boolean isWeekend() {
        return isOnSaturday() || isOnSunday();
    }

    public boolean isWeekday() {
        return !isWeekend();
    }

    public int getDurationInPeriods() {
        return endPeriod - startPeriod + 1;
    }

    public String getTimeSlotDisplay() {
        return String.format("%s: Period %d-%d",
                             dayOfWeek.getDisplayName(), startPeriod, endPeriod);
    }
}