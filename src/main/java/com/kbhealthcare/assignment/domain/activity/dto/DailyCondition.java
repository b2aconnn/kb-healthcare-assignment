package com.kbhealthcare.assignment.domain.activity.dto;

import java.time.LocalDate;

public record DailyCondition(
    String recordkey,
    LocalDate fromDate,
    LocalDate toDate,
    int offset,
    int size
) {
    public static DailyCondition of(
            String recordkey,
            LocalDate fromDate,
            LocalDate toDate,
            int offset,
            int size) {
        return new DailyCondition(
            recordkey,
            fromDate,
            toDate,
            offset,
            size);
    }
}