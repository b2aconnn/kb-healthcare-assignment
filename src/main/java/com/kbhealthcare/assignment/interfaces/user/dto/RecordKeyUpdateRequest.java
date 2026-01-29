package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.RecordKeyModifyCommand;

public record RecordKeyUpdateRequest(
    String email,
    String recordkey
) {
    public RecordKeyModifyCommand toCommand() {
        return new RecordKeyModifyCommand(
            email,
            recordkey);
    }
}