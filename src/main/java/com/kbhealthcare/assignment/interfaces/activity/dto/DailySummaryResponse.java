package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.domain.activity.SourceType;
import com.kbhealthcare.assignment.domain.activity.dto.DailyResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DailySummaryResponse(
    List<DailySummaryDto> summaries,
    long totalCount
) {
    public static DailySummaryResponse from(DailyResult result) {
        List<DailySummaryDto> dailySummaryDtos = result.summaries().stream()
                .map(DailySummaryDto::from)
                .toList();
        return new DailySummaryResponse(dailySummaryDtos, result.totalCount());
    }

    public record DailySummaryDto(
        String recordkey,
        LocalDate date,
        Integer totalSteps,
        BigDecimal totalCalories,
        BigDecimal totalDistance,
        SourceType sourceType
    ) {
        public static DailySummaryDto from(DailyResult.DailySummaryDto dailySummary) {
            return new DailySummaryDto(
                dailySummary.recordkey(),
                dailySummary.date(),
                dailySummary.totalSteps(),
                dailySummary.totalCalories(),
                dailySummary.totalDistance(),
                dailySummary.sourceType()
            );
        }
    }
}