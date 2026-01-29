package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.SourceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ActivityInfo(
    String recordkey,
    Integer steps,
    BigDecimal calories,
    BigDecimal distance,
    ZonedDateTime periodFrom,
    ZonedDateTime periodTo,
    SourceType sourceType
) {
}