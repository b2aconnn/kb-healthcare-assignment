package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.UserCreateCommand;

public record SignUpRequest(
    String name,
    String nickname,
    String email,
    String password
) {
    public UserCreateCommand toCommand() {
        return new UserCreateCommand(
                name,
                nickname,
                email,
                password);
    }
}