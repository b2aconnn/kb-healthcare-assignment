package com.kbhealthcare.assignment.application.user.dto;

public record LoginCommand(
    String email,
    String password
) {
}