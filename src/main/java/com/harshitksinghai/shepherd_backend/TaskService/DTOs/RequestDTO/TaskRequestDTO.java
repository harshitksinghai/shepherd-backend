package com.harshitksinghai.shepherd_backend.TaskService.DTOs.RequestDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private LocalDateTime dueDate;
    private String project;
}
