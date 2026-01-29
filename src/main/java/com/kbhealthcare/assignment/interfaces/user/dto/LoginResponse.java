package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.LoginResult;

public record LoginResponse(
    Long userId,
    String name,
    String nickname,
    String email
) {
    public static LoginResponse from(
            LoginResult result) {
        return new LoginResponse(
                result.userId(),
                result.name(),
                result.nickname(),
                result.email());
    }
}