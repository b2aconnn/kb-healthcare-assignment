package com.kbhealthcare.assignment.domain.activity;

import com.kbhealthcare.assignment.domain.activity.dto.MonthlyCondition;
import org.springframework.data.domain.Page;

public interface MonthlySummaryRepository {
    
    Page<MonthlySummary> findMonthlySummaries(MonthlyCondition condition);
}