package com.kbhealthcare.assignment.interfaces.user;

import com.kbhealthcare.assignment.application.user.UserService;
import com.kbhealthcare.assignment.application.user.dto.UserCreateCommand;
import com.kbhealthcare.assignment.application.user.dto.LoginCommand;
import com.kbhealthcare.assignment.application.user.dto.LoginResult;
import com.kbhealthcare.assignment.application.user.dto.UserCreateResult;
import com.kbhealthcare.assignment.interfaces.user.dto.LoginRequest;
import com.kbhealthcare.assignment.interfaces.user.dto.LoginResponse;
import com.kbhealthcare.assignment.interfaces.user.dto.SignUpRequest;
import com.kbhealthcare.assignment.interfaces.user.dto.SignUpResponse;
import com.kbhealthcare.assignment.interfaces.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(
            @RequestBody SignUpRequest request) {
        
        UserCreateResult result = userService.signup(request.toCommand());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SignUpResponse.from(result)));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request) {
        LoginResult result = userService.login(request.toCommand());
        
        LoginResponse response = new LoginResponse(
            result.userId(),
            result.name(),
            result.nickname(),
            result.email()
        );
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(response));
    }
}