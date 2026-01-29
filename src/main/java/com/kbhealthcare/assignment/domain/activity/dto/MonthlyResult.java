package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.MonthlySummary;
import com.kbhealthcare.assignment.domain.activity.SourceType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public record MonthlyResult(
    List<MonthlySummaryDto> summaries,
    long totalCount
) {
    
    public record MonthlySummaryDto(
        String recordkey,
        String yearMonth,
        Integer totalSteps,
        BigDecimal totalCalories,
        BigDecimal totalDistance,
        SourceType sourceType
    ) {}
    
    public static MonthlyResult from(Page<MonthlySummary> page) {
        List<MonthlySummaryDto> dtoList = page.getContent().stream()
            .map(summary -> new MonthlySummaryDto(
                summary.getRecordkey(),
                summary.getSummaryMonth(),
                summary.getTotalSteps(),
                summary.getTotalCalories(),
                summary.getTotalDistance(),
                summary.getSourceType()
            ))
            .toList();
            
        return new MonthlyResult(dtoList, page.getTotalElements());
    }
}