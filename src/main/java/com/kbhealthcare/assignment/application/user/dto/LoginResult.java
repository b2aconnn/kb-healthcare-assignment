package com.kbhealthcare.assignment.application.user.dto;

import com.kbhealthcare.assignment.domain.user.User;

public record LoginResult(
    Long userId,
    String name,
    String nickname,
    String email
) {
    public static LoginResult from(User user) {
        return new LoginResult(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getEmail());
    }
}