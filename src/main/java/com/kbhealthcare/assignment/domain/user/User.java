package com.kbhealthcare.assignment.domain.user;

import com.kbhealthcare.assignment.domain.user.dto.UserCreateInfo;
import com.kbhealthcare.assignment.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    public static User create(UserCreateInfo info) {
        User user = new User();
        user.name = info.name();
        user.nickname = info.nickname();
        user.email = info.email();
        user.password = info.password();

        return user;
    }
}