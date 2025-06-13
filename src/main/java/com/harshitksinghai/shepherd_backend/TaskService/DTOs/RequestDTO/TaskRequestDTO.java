package com.harshitksinghai.shepherd_backend.TaskService.DTOs.RequestDTO;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String dueDate; // String format
    private String priority;
}
