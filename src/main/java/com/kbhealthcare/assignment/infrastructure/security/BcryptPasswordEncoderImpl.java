package com.kbhealthcare.assignment.infrastructure.security;

import com.kbhealthcare.assignment.support.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordEncoderImpl implements PasswordEncoder {
    
    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();
    
    @Override
    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}