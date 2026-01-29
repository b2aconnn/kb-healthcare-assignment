package com.kbhealthcare.assignment.domain.activity;

import com.kbhealthcare.assignment.domain.activity.dto.DailyCondition;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface DailySummaryRepository {
    
    void updateDailySummaries(List<DailySummary> summaries);

    int saveAggregateMonthlyData(LocalDate startDate, LocalDate endDate);

    Page<DailySummary> findDailySummaries(DailyCondition condition);
}