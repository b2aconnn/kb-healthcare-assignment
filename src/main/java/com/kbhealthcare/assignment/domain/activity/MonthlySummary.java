package com.kbhealthcare.assignment.domain.activity;

import com.kbhealthcare.assignment.domain.activity.dto.MonthlySummaryInfo;
import com.kbhealthcare.assignment.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "monthly_summary")
@Entity
public class MonthlySummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recordkey;

    private String summaryMonth;

    private Integer totalSteps;

    private BigDecimal totalCalories;

    private BigDecimal totalDistance;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    public static MonthlySummary create(MonthlySummaryInfo info) {
        MonthlySummary summary = new MonthlySummary();
        summary.recordkey = info.recordkey();
        summary.summaryMonth = info.yearMonth().toString();
        summary.totalSteps = info.totalSteps();
        summary.totalCalories = info.totalCalories();
        summary.totalDistance = info.totalDistance();
        summary.sourceType = info.sourceType();

        return summary;
    }
}