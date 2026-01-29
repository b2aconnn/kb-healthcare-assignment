package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.domain.activity.dto.DailyCondition;
import java.time.LocalDate;

public record DailySummaryRequest(
    String recordkey,
    String fromDate,
    String toDate,
    int offset,
    int size
) {
    
    public DailyCondition toCondition() {
        LocalDate today = LocalDate.now();
        LocalDate actualFromDate = fromDate != null ? LocalDate.parse(fromDate) : today.minusMonths(3);
        LocalDate actualToDate = toDate != null ? LocalDate.parse(toDate) : today;
        
        return DailyCondition.of(
            recordkey,
            actualFromDate,
            actualToDate,
            offset,
            size
        );
    }
}