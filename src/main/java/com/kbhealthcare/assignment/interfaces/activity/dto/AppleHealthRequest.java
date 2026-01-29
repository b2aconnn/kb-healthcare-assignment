package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.support.DateTimeUtils;
import com.kbhealthcare.assignment.domain.activity.dto.AppleHealthCommand;
import com.kbhealthcare.assignment.domain.activity.dto.AppleHealthCommand.*;

import java.math.BigDecimal;

public record AppleHealthRequest(
    String recordkey,
    AppleHealthDataDto data,
    String type,
    String lastUpdate
) {
    
    public AppleHealthCommand toCommand() {
        return new AppleHealthCommand(
            recordkey,
            data.toDomain(),
            type,
            DateTimeUtils.parseZonedDateTimeWithOffset(lastUpdate)
        );
    }
    
    public record AppleHealthDataDto(
        String memo,
        java.util.List<AppleHealthEntryDto> entries,
        AppleHealthSourceDto source
    ) {
        
        public AppleHealthData toDomain() {
            return new AppleHealthData(
                memo,
                entries.stream().map(AppleHealthEntryDto::toDomain).toList(),
                source.toDomain()
            );
        }
    }
    
    public record AppleHealthEntryDto(
        String steps,
        AppleHealthPeriodDto period,
        AppleHealthValueDto distance,
        AppleHealthValueDto calories
    ) {
        
        public AppleHealthEntry toDomain() {
            return new AppleHealthEntry(
                steps,
                period.toDomain(),
                distance.toDomain(),
                calories.toDomain()
            );
        }
    }
    
    public record AppleHealthPeriodDto(
        String to,
        String from
    ) {
        
        public AppleHealthPeriod toDomain() {
            return new AppleHealthPeriod(
                DateTimeUtils.parseIsoOffsetDateTimeToLocal(to),
                DateTimeUtils.parseIsoOffsetDateTimeToLocal(from)
            );
        }
    }
    
    public record AppleHealthValueDto(
        BigDecimal value,
        String unit
    ) {
        
        public AppleHealthValue toDomain() {
            return new AppleHealthValue(value, unit);
        }
    }
    
    public record AppleHealthSourceDto(
        AppleHealthProductDto product,
        String type,
        Integer mode,
        String name
    ) {
        
        public AppleHealthSource toDomain() {
            return new AppleHealthSource(
                product.toDomain(),
                type,
                mode,
                name
            );
        }
    }
    
    public record AppleHealthProductDto(
        String name,
        String vender
    ) {
        
        public AppleHealthProduct toDomain() {
            return new AppleHealthProduct(name, vender);
        }
    }
}