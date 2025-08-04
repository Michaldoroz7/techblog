package com.doroz.statistics_service.controller;

import com.doroz.statistics_service.event.ActivityStorage;
import com.doroz.statistics_service.models.Activity;
import com.doroz.statistics_service.models.StatisticsResponse;
import com.doroz.statistics_service.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final ActivityStorage storage;

    @GetMapping
    public ResponseEntity<StatisticsResponse> get() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/activity")
    public List<Activity> getRecentActivity() {
        return storage.getAll();
    }
}


