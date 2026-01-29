package com.kbhealthcare.assignment.interfaces.user;

import com.kbhealthcare.assignment.application.user.UserService;
import com.kbhealthcare.assignment.application.user.dto.LoginResult;
import com.kbhealthcare.assignment.application.user.dto.RecordKeyModifyResult;
import com.kbhealthcare.assignment.application.user.dto.UserCreateResult;
import com.kbhealthcare.assignment.interfaces.ApiResponse;
import com.kbhealthcare.assignment.interfaces.user.dto.*;
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
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(LoginResponse.from(result)));
    }

    @PutMapping("/recordkey")
    public ResponseEntity<ApiResponse<RecordKeyUpdateResponse>> updateRecordkey(
            @RequestBody RecordKeyUpdateRequest request) {
        RecordKeyModifyResult result = userService.updateRecordKey(request.toCommand());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(RecordKeyUpdateResponse.from(result)));
    }
}