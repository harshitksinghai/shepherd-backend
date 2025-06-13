package com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponseDTO {
    private String taskId;
    private String title;
    private String dueDate; // String format
    private String priority;
    private String approxTime;
    private String isDivisible;
}