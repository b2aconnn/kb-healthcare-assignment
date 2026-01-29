package com.kbhealthcare.assignment.domain.activity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface HealthActivityRepository {
    
    void bulkInsertIgnore(List<HealthActivity> activities);

    List<HealthActivity> findByRecordkeyAndDateRange(String recordkey, ZonedDateTime from, ZonedDateTime to);

    Optional<HealthActivity> findByRecordkeyAndPeriod(String recordkey, ZonedDateTime periodFrom, ZonedDateTime periodTo);
}