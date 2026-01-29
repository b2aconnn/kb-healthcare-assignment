package com.kbhealthcare.assignment.infrastructure.activity.jpa;

import com.kbhealthcare.assignment.domain.activity.DailySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface DailySummaryJpaRepository extends JpaRepository<DailySummary, Long> {
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO daily_summary (recordkey, date, total_steps, total_calories, total_distance, source_type, created_at, updated_at) " +
                   "VALUES (:recordkey, :date, :totalSteps, :totalCalories, :totalDistance, :sourceType, NOW(), NOW()) " +
                   "ON DUPLICATE KEY UPDATE total_steps = total_steps + VALUES(total_steps), " +
                   "total_calories = total_calories + VALUES(total_calories), " +
                   "total_distance = total_distance + VALUES(total_distance), " +
                   "updated_at = NOW()", nativeQuery = true)
    void upsertDailySummaries(@Param("recordkey") String recordkey,
                              @Param("date") LocalDate date,
                              @Param("totalSteps") Integer totalSteps,
                              @Param("totalCalories") BigDecimal totalCalories,
                              @Param("totalDistance") BigDecimal totalDistance,
                              @Param("sourceType") String sourceType);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO monthly_summary (recordkey, summary_month, total_steps, total_calories, total_distance, source_type, created_at, updated_at) " +
                   "SELECT recordkey, DATE_FORMAT(date, '%Y-%m') as summary_month, " +
                   "       SUM(total_steps) as total_steps, " +
                   "       SUM(total_calories) as total_calories, " +
                   "       SUM(total_distance) as total_distance, " +
                   "       source_type, NOW() as created_at, NOW() as updated_at " +
                   "FROM daily_summary " +
                   "WHERE date BETWEEN :startDate AND :endDate " +
                   "AND NOT EXISTS ( " +
                   "    SELECT 1 FROM monthly_summary ms " +
                   "    WHERE ms.recordkey = daily_summary.recordkey " +
                   "    AND ms.summary_month = DATE_FORMAT(daily_summary.date, '%Y-%m') " +
                   "    AND ms.source_type = daily_summary.source_type " +
                   ") " +
                   "GROUP BY recordkey, DATE_FORMAT(date, '%Y-%m'), source_type " +
                   "ON DUPLICATE KEY UPDATE " +
                   "    total_steps = VALUES(total_steps), " +
                   "    total_calories = VALUES(total_calories), " +
                   "    total_distance = VALUES(total_distance), " +
                   "    updated_at = NOW()", nativeQuery = true)
    int aggregateAndInsertMonthlyData(@Param("startDate") LocalDate startDate, 
                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT d FROM DailySummary d WHERE d.recordkey = :recordkey AND d.date BETWEEN :fromDate AND :toDate ORDER BY d.date")
    Page<DailySummary> findByRecordkeyAndDateBetween(@Param("recordkey") String recordkey,
                                                     @Param("fromDate") LocalDate fromDate,
                                                     @Param("toDate") LocalDate toDate,
                                                     Pageable pageable);
}