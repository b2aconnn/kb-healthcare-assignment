package com.kbhealthcare.assignment.application.user.dto;

import com.kbhealthcare.assignment.domain.user.User;

public record UserCreateResult(
    Long id,
    String name,
    String nickname,
    String email
) {
    public static UserCreateResult from(User user) {
        return new UserCreateResult(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getEmail()
        );
    }
}