package com.example.activitytracker.models.response;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
