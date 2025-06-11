package com.harshitksinghai.shepherd_backend.TaskService.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshitksinghai.shepherd_backend.TaskService.DTOs.RequestDTO.TaskRequestDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.CommonResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.TaskResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.Services.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    
    @Autowired
    TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseDTO> addTask(@RequestBody TaskRequestDTO taskRequestDTO){
        return taskService.addTask(taskRequestDTO);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<CommonResponseDTO> deleteTask(@PathVariable("taskId") String taskId){
        return taskService.deleteTask(taskId);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<TaskResponseDTO>> fetchAllTasks(){
        return taskService.fetchAllTasks();
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponseDTO> updateTask(@PathVariable("taskId") String taskId, @RequestBody TaskRequestDTO taskRequestDTO){
        return taskService.updateTask(taskId, taskRequestDTO);
    }
    
}
