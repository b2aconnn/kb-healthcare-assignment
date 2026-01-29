package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.DailySummary;
import com.kbhealthcare.assignment.domain.activity.SourceType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DailyResult(
    List<DailySummaryDto> summaries,
    long totalCount
) {
    
    public record DailySummaryDto(
        String recordkey,
        LocalDate date,
        Integer totalSteps,
        BigDecimal totalCalories,
        BigDecimal totalDistance,
        SourceType sourceType
    ) {}
    
    public static DailyResult from(Page<DailySummary> page) {
        List<DailySummaryDto> dtoList = page.getContent().stream()
            .map(summary -> new DailySummaryDto(
                summary.getRecordkey(),
                summary.getDate(),
                summary.getTotalSteps(),
                summary.getTotalCalories(),
                summary.getTotalDistance(),
                summary.getSourceType()
            ))
            .toList();
            
        return new DailyResult(dtoList, page.getTotalElements());
    }
}