package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.common.DateTimeUtils;
import com.kbhealthcare.assignment.domain.activity.dto.SamsungHealthCommand;
import com.kbhealthcare.assignment.domain.activity.dto.SamsungHealthCommand.*;

import java.math.BigDecimal;
import java.util.List;

public record SamsungHealthRequest(
    String recordkey,
    SamsungHealthDataDto data,
    String lastUpdate,
    String type
) {
    
    public SamsungHealthCommand toCommand() {
        return new SamsungHealthCommand(
            recordkey,
            data.toDomain(),
            DateTimeUtils.parseZonedDateTimeWithOffset(lastUpdate),
            type
        );
    }
    
    public record SamsungHealthDataDto(
        List<SamsungHealthEntryDto> entries,
        SamsungHealthSourceDto source
    ) {
        
        public SamsungHealthData toDomain() {
            return new SamsungHealthData(
                entries.stream().map(SamsungHealthEntryDto::toDomain).toList(),
                source.toDomain()
            );
        }
    }
    
    public record SamsungHealthEntryDto(
        SamsungHealthPeriodDto period,
        SamsungHealthValueDto distance,
        SamsungHealthValueDto calories,
        Integer steps
    ) {
        
        public SamsungHealthEntry toDomain() {
            return new SamsungHealthEntry(
                period.toDomain(),
                distance.toDomain(),
                calories.toDomain(),
                steps
            );
        }
    }
    
    public record SamsungHealthPeriodDto(
        String from,
        String to
    ) {
        
        public SamsungHealthPeriod toDomain() {
            return new SamsungHealthPeriod(
                DateTimeUtils.parseLocalDateTimeAsZoned(from),
                DateTimeUtils.parseLocalDateTimeAsZoned(to)
            );
        }
    }
    
    public record SamsungHealthValueDto(
        String unit,
        BigDecimal value
    ) {
        
        public SamsungHealthValue toDomain() {
            return new SamsungHealthValue(unit, value);
        }
    }
    
    public record SamsungHealthSourceDto(
        Integer mode,
        SamsungHealthProductDto product,
        String name,
        String type
    ) {
        
        public SamsungHealthSource toDomain() {
            return new SamsungHealthSource(
                mode,
                product.toDomain(),
                name,
                type
            );
        }
    }
    
    public record SamsungHealthProductDto(
        String name,
        String vender
    ) {
        
        public SamsungHealthProduct toDomain() {
            return new SamsungHealthProduct(name, vender);
        }
    }
}