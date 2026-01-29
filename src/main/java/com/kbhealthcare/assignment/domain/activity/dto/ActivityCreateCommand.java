package com.kbhealthcare.assignment.domain.activity.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record ActivityCreateCommand(
    String recordkey,
    List<ActivityEntryCommand> entries,
    String sourceType
) {
    
    public record ActivityEntryCommand(
        ZonedDateTime periodFrom,
        ZonedDateTime periodTo,
        Integer steps,
        BigDecimal calories,
        BigDecimal distance
    ) {}
}