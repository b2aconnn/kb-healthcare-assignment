package com.kbhealthcare.assignment.infrastructure.activity.jpa;

import com.kbhealthcare.assignment.domain.activity.MonthlySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlySummaryJpaRepository extends JpaRepository<MonthlySummary, Long> {
    
    @Query("SELECT m FROM MonthlySummary m " +
            "WHERE m.recordkey = :recordkey " +
            "AND m.summaryMonth BETWEEN :fromYearMonth AND :toYearMonth " +
            "ORDER BY m.summaryMonth")
    Page<MonthlySummary> findByRecordkeyAndYearMonthBetween(
            @Param("recordkey") String recordkey,
            @Param("fromYearMonth") String fromYearMonth,
            @Param("toYearMonth") String toYearMonth,
            Pageable pageable);
}