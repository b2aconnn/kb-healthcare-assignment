package com.kbhealthcare.assignment.application.activity;

import com.kbhealthcare.assignment.application.aggregation.AggregationService;
import com.kbhealthcare.assignment.domain.activity.*;
import com.kbhealthcare.assignment.domain.activity.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthActivityService {
    
    private final HealthActivityRepository healthActivityRepository;
    private final DailySummaryRepository dailySummaryRepository;
    private final MonthlySummaryRepository monthlySummaryRepository;
    private final AggregationService aggregationService;

    @Transactional
    public ActivityCreateResult saveSamsungHealthData(SamsungHealthCommand command) {
        List<HealthActivity> activities = command.toEntities();
        
        healthActivityRepository.bulkInsertIgnore(activities);

        aggregateDaily(activities);

        return ActivityCreateResult.from(activities);
    }

    @Transactional
    public ActivityCreateResult saveAppleHealthData(AppleHealthCommand command) {
        List<HealthActivity> activities = command.toEntities();
        
        healthActivityRepository.bulkInsertIgnore(activities);

        aggregateDaily(activities);

        return ActivityCreateResult.from(activities);
    }

    @Transactional
    public void aggregateDaily(List<HealthActivity> activities) {
        Map<LocalDate, DailySummary> dailySummaries = aggregationService.aggregateDaily(activities);
        dailySummaryRepository.updateDailySummaries(dailySummaries.values().stream().toList());
    }

    @Transactional
    public void aggregateMonthly(YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        try {
            int affectedRows = dailySummaryRepository.saveAggregateMonthlyData(startDate, endDate);
            
            log.info("Monthly aggregation completed: {} records processed (period: {} ~ {})", 
                    affectedRows, startDate, endDate);
                    
        } catch (Exception e) {
            log.error("Monthly aggregation failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    public DailyResult getDailySummaries(DailyCondition condition) {
        Page<DailySummary> dailySummaries = dailySummaryRepository.findDailySummaries(condition);
        return DailyResult.from(dailySummaries);
    }

    public MonthlyResult getMonthlySummaries(MonthlyCondition condition) {
        Page<MonthlySummary> monthlySummaries = monthlySummaryRepository.findMonthlySummaries(condition);
        return MonthlyResult.from(monthlySummaries);
    }
}