package com.kbhealthcare.assignment.domain.user;

import java.util.Optional;

public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByNickname(String nickname);

    boolean existsByRecordKey(String recordKey);
}