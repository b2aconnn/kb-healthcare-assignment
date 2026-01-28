package com.kbhealthcare.assignment.interfaces.user.dto;

import com.kbhealthcare.assignment.application.user.dto.LoginCommand;

public record LoginRequest(
    String email,
    String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(
            email,
            password);
    }
}