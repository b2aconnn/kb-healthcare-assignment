package com.kbhealthcare.assignment.application.user.dto;

public record UserCreateCommand(
    String name,
    String nickname,
    String email,
    String password
) {
}