package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.HealthActivity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static com.kbhealthcare.assignment.domain.activity.SourceType.APPLE;

public record AppleHealthCommand(
    String recordkey,
    AppleHealthData data,
    String type,
    ZonedDateTime lastUpdate
) {
    
    public record AppleHealthData(
        String memo,
        List<AppleHealthEntry> entries,
        AppleHealthSource source
    ) {}
    
    public record AppleHealthEntry(
        String steps,
        AppleHealthPeriod period,
        AppleHealthValue distance,
        AppleHealthValue calories
    ) {
        public LocalDateTime getPeriodFrom() {
            return period.from();
        }
        
        public LocalDateTime getPeriodTo() {
            return period.to();
        }
        
        public BigDecimal getDistance() {
            return distance.value();
        }
        
        public BigDecimal getCalories() {
            return calories.value();
        }
        
        public Integer getSteps() {
            return Math.round(Float.parseFloat(steps));
        }
    }
    
    public record AppleHealthPeriod(
        LocalDateTime to,
        LocalDateTime from
    ) {}
    
    public record AppleHealthValue(
        BigDecimal value,
        String unit
    ) {}
    
    public record AppleHealthSource(
        AppleHealthProduct product,
        String type,
        Integer mode,
        String name
    ) {}
    
    public record AppleHealthProduct(
        String name,
        String vender
    ) {}

    public List<HealthActivity> toEntities() {
        return data.entries().stream()
                .map(entry -> HealthActivity.create(
                    new ActivityInfo(
                        recordkey,
                        entry.getSteps(),
                        entry.getCalories(),
                        entry.getDistance(),
                        entry.getPeriodFrom().atZone(ZoneOffset.UTC),
                        entry.getPeriodTo().atZone(ZoneOffset.UTC),
                        APPLE
                    )
                ))
                .map(HealthActivity.class::cast)
                .toList();
    }
}