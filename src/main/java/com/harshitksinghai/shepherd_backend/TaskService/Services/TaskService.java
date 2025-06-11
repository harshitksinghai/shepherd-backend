package com.harshitksinghai.shepherd_backend.TaskService.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.harshitksinghai.shepherd_backend.TaskService.DTOs.RequestDTO.TaskRequestDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.CommonResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.TaskResponseDTO;


public interface TaskService {

    ResponseEntity<CommonResponseDTO> addTask(TaskRequestDTO taskRequestDTO);

    ResponseEntity<CommonResponseDTO> deleteTask(String taskId);

    ResponseEntity<List<TaskResponseDTO>> fetchAllTasks();

    ResponseEntity<CommonResponseDTO> updateTask(String taskId, TaskRequestDTO taskRequestDTO);
    
}
