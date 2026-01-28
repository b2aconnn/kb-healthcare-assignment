package com.kbhealthcare.assignment.domain.user.dto;

public record UserCreateInfo(
    String name,
    String nickname,
    String email,
    String password
) {
}