package com.kbhealthcare.assignment.interfaces.activity;

import com.kbhealthcare.assignment.application.activity.HealthActivityService;
import com.kbhealthcare.assignment.domain.activity.dto.*;
import com.kbhealthcare.assignment.interfaces.activity.dto.*;
import com.kbhealthcare.assignment.interfaces.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    
    private final HealthActivityService healthActivityService;
    
    @PostMapping("/samsung")
    public ResponseEntity<ApiResponse<ActivityCreateResponse>> saveSamsungHealthData(
            @RequestBody SamsungHealthRequest request) {

        SamsungHealthCommand command = request.toCommand();
        ActivityCreateResult result = healthActivityService.saveSamsungHealthData(command);
        
        ActivityCreateResponse response = ActivityCreateResponse.from(result);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response));
    }
    
    @PostMapping("/apple")
    public ResponseEntity<ApiResponse<ActivityCreateResponse>> saveAppleHealthData(
            @RequestBody AppleHealthRequest request) {
        
        AppleHealthCommand command = request.toCommand();
        ActivityCreateResult result = healthActivityService.saveAppleHealthData(command);
        
        ActivityCreateResponse response = ActivityCreateResponse.from(result);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response));
    }
    
    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<DailySummaryResponse>> getDailySummaries(
            @RequestParam String recordkey,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int size) {

        DailySummaryRequest request = new DailySummaryRequest(recordkey, fromDate, toDate, offset, size);
        DailyCondition condition = request.toCondition();
        DailyResult result = healthActivityService.getDailySummaries(condition);

        DailySummaryResponse response = DailySummaryResponse.from(result);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(response));
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<MonthlySummaryResponse>> getMonthlySummaries(
            @RequestParam String recordkey,
            @RequestParam(required = false) String fromYearMonth,
            @RequestParam(required = false) String toYearMonth,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int size) {
        
        MonthlySummaryRequest request = new MonthlySummaryRequest(recordkey, fromYearMonth, toYearMonth, offset, size);
        MonthlyCondition condition = request.toCondition();
        MonthlyResult result = healthActivityService.getMonthlySummaries(condition);
        
        MonthlySummaryResponse response = MonthlySummaryResponse.from(result);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(response));
    }
}