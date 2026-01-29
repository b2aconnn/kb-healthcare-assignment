package com.kbhealthcare.assignment.application.user.dto;

public record RecordKeyModifyCommand(
    String email,
    String recordKey
) {
}