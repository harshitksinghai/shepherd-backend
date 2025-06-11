package com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponseDTO {
    private String title;
    private LocalDateTime dueDate;
    private String project;
}
