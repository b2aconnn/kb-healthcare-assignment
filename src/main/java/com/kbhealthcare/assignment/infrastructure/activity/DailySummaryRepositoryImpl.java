package com.kbhealthcare.assignment.infrastructure.activity;

import com.kbhealthcare.assignment.domain.activity.DailySummary;
import com.kbhealthcare.assignment.domain.activity.DailySummaryRepository;
import com.kbhealthcare.assignment.domain.activity.dto.DailyCondition;
import com.kbhealthcare.assignment.infrastructure.activity.jpa.DailySummaryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailySummaryRepositoryImpl implements DailySummaryRepository {
    
    private final DailySummaryJpaRepository jpaRepository;
    
    @Override
    public void updateDailySummaries(List<DailySummary> summaries) {
        for (DailySummary summary : summaries) {
            jpaRepository.upsertDailySummaries(
                summary.getRecordkey(),
                summary.getDate(),
                summary.getTotalSteps(),
                summary.getTotalCalories(),
                summary.getTotalDistance(),
                summary.getSourceType().toString()
            );
        }
    }
    
    @Override
    public int saveAggregateMonthlyData(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.aggregateAndInsertMonthlyData(startDate, endDate);
    }

    @Override
    public Page<DailySummary> findDailySummaries(DailyCondition condition) {
        int page = condition.offset() / condition.size();
        Pageable pageable = PageRequest.of(page, condition.size());
        return jpaRepository.findByRecordkeyAndDateBetween(
                condition.recordkey(),
                condition.fromDate(),
                condition.toDate(),
                pageable
        );
    }
}