package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.HealthActivity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static com.kbhealthcare.assignment.domain.activity.SourceType.SAMSUNG;

public record SamsungHealthCommand(
    String recordkey,
    SamsungHealthData data,
    ZonedDateTime lastUpdate,
    String type
) {
    
    public record SamsungHealthData(
        List<SamsungHealthEntry> entries,
        SamsungHealthSource source
    ) {}
    
    public record SamsungHealthEntry(
        SamsungHealthPeriod period,
        SamsungHealthValue distance,
        SamsungHealthValue calories,
        Integer steps
    ) {
        public ZonedDateTime getPeriodFrom() {
            return period.from();
        }
        
        public ZonedDateTime getPeriodTo() {
            return period.to();
        }
        
        public BigDecimal getDistance() {
            return distance.value();
        }
        
        public BigDecimal getCalories() {
            return calories.value();
        }
    }
    
    public record SamsungHealthPeriod(
        ZonedDateTime from,
        ZonedDateTime to
    ) {}
    
    public record SamsungHealthValue(
        String unit,
        BigDecimal value
    ) {}
    
    public record SamsungHealthSource(
        Integer mode,
        SamsungHealthProduct product,
        String name,
        String type
    ) {}
    
    public record SamsungHealthProduct(
        String name,
        String vender
    ) {}

    public List<HealthActivity> toEntities() {
        return data.entries().stream()
                .map(entry -> HealthActivity.create(
                    new ActivityInfo(
                        recordkey,
                        entry.steps(),
                        entry.getCalories(),
                        entry.getDistance(),
                        entry.getPeriodFrom(),
                        entry.getPeriodTo(),
                        SAMSUNG
                    )
                ))
                .map(HealthActivity.class::cast)
                .toList();
    }
}