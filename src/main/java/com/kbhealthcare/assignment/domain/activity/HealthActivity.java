package com.kbhealthcare.assignment.domain.activity;

import com.kbhealthcare.assignment.domain.activity.dto.ActivityInfo;
import com.kbhealthcare.assignment.domain.activity.dto.DailySummaryInfo;
import com.kbhealthcare.assignment.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "health_activity")
@Entity
public class HealthActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recordkey;

    private Integer steps;

    private BigDecimal calories;

    private BigDecimal distance;

    private ZonedDateTime periodFrom;

    private ZonedDateTime periodTo;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    public static HealthActivity create(ActivityInfo info) {
        HealthActivity activity = new HealthActivity();
        activity.recordkey = info.recordkey();
        activity.steps = info.steps();
        activity.calories = info.calories();
        activity.distance = info.distance();
        activity.periodFrom = info.periodFrom();
        activity.periodTo = info.periodTo();
        activity.sourceType = info.sourceType();

        return activity;
    }

    public DailySummary createDailySummary() {
        return DailySummary.create(DailySummaryInfo.builder()
            .recordkey(recordkey)
            .date(periodFrom.toLocalDate())
            .totalSteps(steps)
            .totalCalories(calories)
            .totalDistance(distance)
            .sourceType(sourceType)
            .build());
    }
}