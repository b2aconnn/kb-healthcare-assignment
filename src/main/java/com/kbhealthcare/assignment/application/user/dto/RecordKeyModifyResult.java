package com.kbhealthcare.assignment.application.user.dto;

import com.kbhealthcare.assignment.domain.user.User;

public record RecordKeyModifyResult(
    String email,
    String recordKey
) {
    public static RecordKeyModifyResult from(User user) {
        return new RecordKeyModifyResult(
                user.getEmail(),
                user.getRecordkey()
        );
    }
}