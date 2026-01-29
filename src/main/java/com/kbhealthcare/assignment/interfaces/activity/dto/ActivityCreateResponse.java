package com.kbhealthcare.assignment.interfaces.activity.dto;

import com.kbhealthcare.assignment.domain.activity.dto.ActivityCreateResult;
import com.kbhealthcare.assignment.domain.activity.SourceType;

public record ActivityCreateResponse(
    String recordkey,
    Integer totalCount,
    SourceType sourceType
) {
    public static ActivityCreateResponse from(ActivityCreateResult result) {
        return new ActivityCreateResponse(
            result.recordkey(),
            result.totalCount(),
            result.sourceType()
        );
    }
}