package org.lms.backend.calendar.entity;

import jakarta.persistence.*;
import org.lms.backend.classroom.entity.Class;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "class_schedules")
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classScheduleId;
    @OneToOne
    @JoinColumn(name = "class_id")
    private Class classOf;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private Duration duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private RecurrenceType recurrenceType;

    public ClassSchedule(
            Class classOf,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            Duration duration,
            LocalDate startDate,
            LocalDate endDate,
            RecurrenceType recurrenceType
    ) {
        this.setClassOf(classOf);
        this.setDayOfWeek(dayOfWeek);
        this.setStartTime(startTime);
        this.setDuration(duration);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setRecurrenceType(recurrenceType);
    }

    public ClassSchedule() {
    }

    public Long getClassScheduleId() {
        return classScheduleId;
    }

    public Class getClassOf() {
        return classOf;
    }

    public void setClassOf(Class classOf) {
        this.classOf = classOf;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RecurrenceType getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
    }
}
