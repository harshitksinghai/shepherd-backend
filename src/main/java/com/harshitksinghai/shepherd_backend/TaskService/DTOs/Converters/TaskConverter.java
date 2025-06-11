package com.harshitksinghai.shepherd_backend.TaskService.DTOs.Converters;

import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.TaskResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.Models.Task;

public class TaskConverter {
    public static TaskResponseDTO TaskToTaskResponseDTO(Task task){
        return TaskResponseDTO.builder()
            .title(task.getTitle())
            .dueDate(task.getDueDate())
            .priority(task.getPriority())
            .build();
    }
}
