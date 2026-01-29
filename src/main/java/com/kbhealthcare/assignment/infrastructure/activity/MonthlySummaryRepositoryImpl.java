package com.kbhealthcare.assignment.infrastructure.activity;

import com.kbhealthcare.assignment.domain.activity.MonthlySummary;
import com.kbhealthcare.assignment.domain.activity.MonthlySummaryRepository;
import com.kbhealthcare.assignment.domain.activity.dto.MonthlyCondition;
import com.kbhealthcare.assignment.infrastructure.activity.jpa.MonthlySummaryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonthlySummaryRepositoryImpl implements MonthlySummaryRepository {
    
    private final MonthlySummaryJpaRepository jpaRepository;
    
    @Override
    public Page<MonthlySummary> findMonthlySummaries(MonthlyCondition condition) {
        int page = condition.offset() / condition.size();
        Pageable pageable = PageRequest.of(page, condition.size());
        return jpaRepository.findByRecordkeyAndYearMonthBetween(
            condition.recordkey(),
            condition.fromYearMonth().toString(),
            condition.toYearMonth().toString(),
            pageable
        );
    }
}