package com.kbhealthcare.assignment.interfaces.user.dto;

public record LoginResponse(
    Long userId,
    String name,
    String nickname,
    String email
) {
}