package com.kbhealthcare.assignment.infrastructure.activity;

import com.kbhealthcare.assignment.domain.activity.HealthActivity;
import com.kbhealthcare.assignment.domain.activity.HealthActivityRepository;
import com.kbhealthcare.assignment.infrastructure.activity.jpa.HealthActivityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HealthActivityRepositoryImpl implements HealthActivityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final HealthActivityJpaRepository jpaRepository;

    private static final int BATCH_SIZE = 300;

    @Override
    public void bulkInsertIgnore(List<HealthActivity> activities) {
        if (activities == null || activities.isEmpty()) {
            return;
        }

        String sql = """
            INSERT IGNORE INTO health_activity
            (recordkey, steps, calories, distance, period_from, period_to, source_type, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        List<List<HealthActivity>> batches = partitionList(activities, BATCH_SIZE);

        for (List<HealthActivity> batch : batches) {
            jdbcTemplate.batchUpdate(
                    sql,
                    batch,
                    batch.size(),
                    (ps, activity) -> {
                        ps.setString(1, activity.getRecordkey());
                        ps.setInt(2, activity.getSteps());
                        ps.setBigDecimal(3, activity.getCalories());
                        ps.setBigDecimal(4, activity.getDistance());

                        // ZonedDateTime → UTC Timestamp (명시적 변환)
                        ps.setTimestamp(5, toUtcTimestamp(activity.getPeriodFrom()));
                        ps.setTimestamp(6, toUtcTimestamp(activity.getPeriodTo()));

                        ps.setString(7, activity.getSourceType().name());
                        ps.setTimestamp(8, toUtcTimestamp(activity.getCreatedAt()));
                        ps.setTimestamp(9, toUtcTimestamp(activity.getUpdatedAt()));
                    }
            );
        }
    }

    @Override
    public List<HealthActivity> findByRecordkeyAndDateRange(
            String recordkey,
            ZonedDateTime from,
            ZonedDateTime to
    ) {
        return jpaRepository.findByRecordkeyAndPeriodFromBetween(recordkey, from, to);
    }

    @Override
    public Optional<HealthActivity> findByRecordkeyAndPeriod(
            String recordkey,
            ZonedDateTime periodFrom,
            ZonedDateTime periodTo
    ) {
        return jpaRepository.findByRecordkeyAndPeriod(recordkey, periodFrom, periodTo);
    }

    private Timestamp toUtcTimestamp(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return Timestamp.from(
                zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant()
        );
    }

    private <T> List<List<T>> partitionList(List<T> list, int batchSize) {
        int size = list.size();
        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < size; i += batchSize) {
            partitions.add(list.subList(i, Math.min(i + batchSize, size)));
        }

        return partitions;
    }
}