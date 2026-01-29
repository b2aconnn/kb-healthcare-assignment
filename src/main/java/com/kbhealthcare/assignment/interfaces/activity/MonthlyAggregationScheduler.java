package com.kbhealthcare.assignment.interfaces.activity;

import com.kbhealthcare.assignment.application.activity.HealthActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyAggregationScheduler {
    
    private final HealthActivityService healthActivityService;
    
    @Scheduled(cron = "0 0 2 1 * ?") // 매월 1일 새벽 2시
    public void aggregateMonthlyData() {
        log.info("Starting monthly aggregation task");
        
        try {
            YearMonth lastMonth = YearMonth.now().minusMonths(1);
            healthActivityService.aggregateMonthly(lastMonth);
            log.info("Monthly aggregation completed successfully");
        } catch (Exception e) {
            log.error("Monthly aggregation failed", e);
        }
    }
}