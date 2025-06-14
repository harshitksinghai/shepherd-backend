package com.harshitksinghai.shepherd_backend.TaskService.DTOs.Converters;

import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.TaskResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.Models.Task;

public class TaskConverter {
    public static TaskResponseDTO TaskToTaskResponseDTO(Task task){
        return TaskResponseDTO.builder()
            .taskId(task.getTaskId())
            .title(task.getTitle())
            .dueDate(task.getDueDate().toString())
            .priority(task.getPriority())
            .approxTime(task.getApproxTime())
            .isDivisible(task.getIsDivisible())
            .build();
    }
}
