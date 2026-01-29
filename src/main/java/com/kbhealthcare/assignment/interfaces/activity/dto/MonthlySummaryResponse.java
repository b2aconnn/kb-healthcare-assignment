package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.domain.activity.SourceType;
import com.kbhealthcare.assignment.domain.activity.dto.MonthlyResult;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record MonthlySummaryResponse(
    List<MonthlySummaryDto> summaries,
    long totalCount
) {
    public static MonthlySummaryResponse from(MonthlyResult result) {
        List<MonthlySummaryDto> monthlySummaryDtos = result.summaries().stream()
                .map(MonthlySummaryDto::from)
                .toList();
        return new MonthlySummaryResponse(monthlySummaryDtos, result.totalCount());
    }

    public record MonthlySummaryDto(
        String recordkey,
        String yearMonth,
        Integer totalSteps,
        BigDecimal totalCalories,
        BigDecimal totalDistance,
        SourceType sourceType
    ) {
        public static MonthlySummaryDto from(MonthlyResult.MonthlySummaryDto monthlySummary) {
            return new MonthlySummaryDto(
                monthlySummary.recordkey(),
                monthlySummary.yearMonth(),
                monthlySummary.totalSteps(),
                monthlySummary.totalCalories(),
                monthlySummary.totalDistance(),
                monthlySummary.sourceType()
            );
        }
    }
}