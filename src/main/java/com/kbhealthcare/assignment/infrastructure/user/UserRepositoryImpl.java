package com.kbhealthcare.assignment.infrastructure.user;

import com.kbhealthcare.assignment.domain.user.User;
import com.kbhealthcare.assignment.domain.user.UserRepository;
import com.kbhealthcare.assignment.infrastructure.user.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository jpaRepository;
    
    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByNickname(String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByRecordKey(String recordKey) {
        return jpaRepository.existsByRecordkey(recordKey);
    }
}