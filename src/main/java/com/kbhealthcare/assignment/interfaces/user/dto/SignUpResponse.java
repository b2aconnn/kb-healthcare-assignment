package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.UserCreateResult;

public record SignUpResponse(
    Long userId,
    String name,
    String nickname,
    String email
) {
    public static SignUpResponse from(
            UserCreateResult result) {
        return new SignUpResponse(
                result.id(),
                result.email(),
                result.name(),
                result.nickname());
    }
}