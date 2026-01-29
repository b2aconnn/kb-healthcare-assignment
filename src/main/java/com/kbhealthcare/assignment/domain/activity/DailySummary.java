package com.kbhealthcare.assignment.domain.activity;

import com.kbhealthcare.assignment.domain.activity.dto.DailySummaryInfo;
import com.kbhealthcare.assignment.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "daily_summary")
@Entity
public class DailySummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recordkey;

    private LocalDate date;

    private Integer totalSteps;

    private BigDecimal totalCalories;

    private BigDecimal totalDistance;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    public static DailySummary create(DailySummaryInfo info) {
        DailySummary summary = new DailySummary();
        summary.recordkey = info.recordkey();
        summary.date = info.date();
        summary.totalSteps = info.totalSteps();
        summary.totalCalories = info.totalCalories();
        summary.totalDistance = info.totalDistance();
        summary.sourceType = info.sourceType();

        return summary;
    }

    public static DailySummary empty() {
        DailySummary summary = new DailySummary();
        summary.totalSteps = 0;
        summary.totalCalories = BigDecimal.ZERO;
        summary.totalDistance = BigDecimal.ZERO;
        summary.date = null;
        summary.recordkey = null;
        summary.sourceType = null;

        return summary;
    }

    public DailySummary merge(DailySummary other) {
        DailySummary merged = new DailySummary();
        merged.recordkey = this.recordkey != null ? this.recordkey : other.recordkey;
        merged.date = this.date != null ? this.date : other.date;
        merged.sourceType = this.sourceType != null ? this.sourceType : other.sourceType;
        merged.totalSteps = (this.totalSteps != null ? this.totalSteps : 0) + (other.totalSteps != null ? other.totalSteps : 0);
        merged.totalCalories = (this.totalCalories != null ? this.totalCalories : BigDecimal.ZERO).add(other.totalCalories != null ? other.totalCalories : BigDecimal.ZERO);
        merged.totalDistance = (this.totalDistance != null ? this.totalDistance : BigDecimal.ZERO).add(other.totalDistance != null ? other.totalDistance : BigDecimal.ZERO);
        return merged;
    }

    public static DailySummary fromActivity(HealthActivity activity) {
        return activity.createDailySummary();
    }
}