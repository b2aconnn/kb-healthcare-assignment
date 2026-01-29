package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.RecordKeyModifyResult;

public record RecordKeyUpdateResponse(
    String email,
    String password
) {
    public static RecordKeyUpdateResponse from(RecordKeyModifyResult result) {
        return new RecordKeyUpdateResponse(
                result.email(),
                result.recordKey());
    }
}