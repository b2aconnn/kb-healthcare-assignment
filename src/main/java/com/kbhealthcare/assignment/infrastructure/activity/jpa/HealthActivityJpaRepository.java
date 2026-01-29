package com.kbhealthcare.assignment.infrastructure.activity.jpa;

import com.kbhealthcare.assignment.domain.activity.HealthActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthActivityJpaRepository extends JpaRepository<HealthActivity, Long> {
    
    @Query("SELECT h FROM HealthActivity h WHERE h.recordkey = :recordkey AND h.periodFrom BETWEEN :from AND :to")
    List<HealthActivity> findByRecordkeyAndPeriodFromBetween(@Param("recordkey") String recordkey, 
                                                            @Param("from") ZonedDateTime from, 
                                                            @Param("to") ZonedDateTime to);
    
    @Query("SELECT h FROM HealthActivity h WHERE h.recordkey = :recordkey AND h.periodFrom = :periodFrom AND h.periodTo = :periodTo")
    Optional<HealthActivity> findByRecordkeyAndPeriod(@Param("recordkey") String recordkey,
                                                      @Param("periodFrom") ZonedDateTime periodFrom,
                                                      @Param("periodTo") ZonedDateTime periodTo);
}