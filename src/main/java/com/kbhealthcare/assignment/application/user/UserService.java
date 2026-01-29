package com.kbhealthcare.assignment.application.user;

import com.kbhealthcare.assignment.application.user.dto.*;
import com.kbhealthcare.assignment.support.PasswordEncoder;
import com.kbhealthcare.assignment.domain.user.User;
import com.kbhealthcare.assignment.domain.user.UserRepository;
import com.kbhealthcare.assignment.domain.user.dto.UserCreateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResult signup(UserCreateCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already exists: " + command.email());
        }

        String encodedPassword = passwordEncoder.encode(command.password());

        UserCreateInfo userInfo = new UserCreateInfo(
                command.name(),
                command.nickname(),
                command.email(),
                encodedPassword
        );

        User user = User.create(userInfo);
        User savedUser = userRepository.save(user);

        return UserCreateResult.from(savedUser);
    }

    public LoginResult login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return LoginResult.from(user);
    }

    @Transactional
    public RecordKeyModifyResult updateRecordKey(RecordKeyModifyCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (userRepository.existsByRecordKey(command.recordKey())) {
            throw new IllegalArgumentException("Recordkey already exists: " + command.recordKey());
        }

        user.updateRecordkey(command.recordKey());

        return RecordKeyModifyResult.from(user);
    }
}