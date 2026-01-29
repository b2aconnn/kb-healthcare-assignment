package com.kbhealthcare.assignment.application.aggregation;

import com.kbhealthcare.assignment.domain.activity.DailySummary;
import com.kbhealthcare.assignment.domain.activity.HealthActivity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregationService {
    
    public Map<LocalDate, DailySummary> aggregateDaily(List<HealthActivity> activities) {
        return activities.stream()
            .collect(Collectors.groupingBy(
                activity -> activity.getPeriodFrom().toLocalDate(),
                Collectors.reducing(
                    DailySummary.empty(),
                    DailySummary::fromActivity,
                    DailySummary::merge
                )
            ));
    }
}