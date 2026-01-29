package com.kbhealthcare.assignment.domain.activity.dto;

import com.kbhealthcare.assignment.domain.activity.HealthActivity;
import com.kbhealthcare.assignment.domain.activity.SourceType;

import java.util.List;

public record ActivityCreateResult(
    String recordkey,
    Integer totalCount,
    SourceType sourceType
) {
    
    public static ActivityCreateResult from(List<HealthActivity> activities) {
        if (activities.isEmpty()) {
            return new ActivityCreateResult("", 0, null);
        }
        
        String recordkey = activities.get(0).getRecordkey();
        SourceType sourceType = activities.get(0).getSourceType();
        
        return new ActivityCreateResult(recordkey, activities.size(), sourceType);
    }
}