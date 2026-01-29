package com.kbhealthcare.assignment.domain.activity.dto;

import java.time.YearMonth;

public record MonthlyCondition(
    String recordkey,
    YearMonth fromYearMonth,
    YearMonth toYearMonth,
    int offset,
    int size
) {
    
    public static MonthlyCondition of(
            String recordkey,
            YearMonth fromYearMonth,
            YearMonth toYearMonth) {
        return new MonthlyCondition(
            recordkey,
            fromYearMonth,
            toYearMonth,
            0,
            20);
    }
    
    public static MonthlyCondition of(
            String recordkey,
            YearMonth fromYearMonth,
            YearMonth toYearMonth,
            int offset,
            int size) {
        return new MonthlyCondition(
            recordkey,
            fromYearMonth,
            toYearMonth,
            offset,
            size);
    }
}