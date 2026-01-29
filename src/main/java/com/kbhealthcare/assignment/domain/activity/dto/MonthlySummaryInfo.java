package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.SourceType;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MonthlySummaryInfo(
    String recordkey,
    YearMonth yearMonth,
    Integer totalSteps,
    BigDecimal totalCalories,
    BigDecimal totalDistance,
    SourceType sourceType
) {
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String recordkey;
        private YearMonth yearMonth;
        private Integer totalSteps;
        private BigDecimal totalCalories;
        private BigDecimal totalDistance;
        private SourceType sourceType;

        public Builder recordkey(String recordkey) {
            this.recordkey = recordkey;
            return this;
        }

        public Builder yearMonth(YearMonth yearMonth) {
            this.yearMonth = yearMonth;
            return this;
        }

        public Builder totalSteps(Integer totalSteps) {
            this.totalSteps = totalSteps;
            return this;
        }

        public Builder totalCalories(BigDecimal totalCalories) {
            this.totalCalories = totalCalories;
            return this;
        }

        public Builder totalDistance(BigDecimal totalDistance) {
            this.totalDistance = totalDistance;
            return this;
        }

        public Builder sourceType(SourceType sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        public MonthlySummaryInfo build() {
            return new MonthlySummaryInfo(recordkey, yearMonth, totalSteps, totalCalories, totalDistance, sourceType);
        }
    }
}