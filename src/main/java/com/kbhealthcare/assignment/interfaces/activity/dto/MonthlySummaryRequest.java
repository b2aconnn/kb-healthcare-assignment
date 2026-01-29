package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.domain.activity.dto.MonthlyCondition;
import java.time.YearMonth;

public record MonthlySummaryRequest(
    String recordkey,
    String fromYearMonth,
    String toYearMonth,
    int offset,
    int size
) {
    
    public MonthlyCondition toCondition() {
        YearMonth currentMonth = YearMonth.now();
        YearMonth actualFromMonth = fromYearMonth != null ? YearMonth.parse(fromYearMonth) : currentMonth.minusMonths(3);
        YearMonth actualToMonth = toYearMonth != null ? YearMonth.parse(toYearMonth) : currentMonth;
        
        return MonthlyCondition.of(
            recordkey,
            actualFromMonth,
            actualToMonth,
            offset,
            size
        );
    }
}