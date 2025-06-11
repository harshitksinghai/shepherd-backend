package com.harshitksinghai.shepherd_backend.TaskService.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harshitksinghai.shepherd_backend.TaskService.DTOs.Converters.TaskConverter;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.RequestDTO.TaskRequestDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.CommonResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO.TaskResponseDTO;
import com.harshitksinghai.shepherd_backend.TaskService.Models.Task;
import com.harshitksinghai.shepherd_backend.TaskService.Repositories.TaskRepository;
import com.harshitksinghai.shepherd_backend.TaskService.Services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskRepository taskRepository;

    @Override
    public ResponseEntity<CommonResponseDTO> addTask(TaskRequestDTO taskRequestDTO) {
        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        try {
            Task task = new Task();
            task.setTaskId(UUID.randomUUID().toString());
            task.setTitle(taskRequestDTO.getTitle());
            task.setDueDate(taskRequestDTO.getDueDate());
            task.setPriority(taskRequestDTO.getPriority());

            taskRepository.save(task);

            commonResponseDTO.setSuccess(true);
            commonResponseDTO.setMessage("Task Added Successfully!");

            return new ResponseEntity<>(commonResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to add task", e);
            commonResponseDTO.setSuccess(false);
            commonResponseDTO.setMessage("Unable to add task");
            return new ResponseEntity<>(commonResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<CommonResponseDTO> deleteTask(String taskId) {
        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        try {
            taskRepository.deleteByTaskId(taskId);
            commonResponseDTO.setSuccess(true);
            commonResponseDTO.setMessage("Task Deleted Successfully!");
            return new ResponseEntity<>(commonResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to delete task", e);
            commonResponseDTO.setSuccess(false);
            commonResponseDTO.setMessage("Unable to delete task");
            return new ResponseEntity<>(commonResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TaskResponseDTO>> fetchAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        List<TaskResponseDTO> taskResponseDTOs = tasks.stream()
                .map(TaskConverter::TaskToTaskResponseDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskResponseDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponseDTO> updateTask(String taskId, TaskRequestDTO taskRequestDTO) {
        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();

        try {
            Optional<Task> taskOpt = taskRepository.findByTaskId(taskId);
            if (taskOpt.isEmpty()) {
                commonResponseDTO.setSuccess(false);
                commonResponseDTO.setMessage("Task with provided taskId does not exist in db!");
                return new ResponseEntity<>(commonResponseDTO, HttpStatus.BAD_REQUEST);
            }

            Task task = taskOpt.get();
            task.setTitle(taskRequestDTO.getTitle());
            task.setDueDate(taskRequestDTO.getDueDate());
            task.setPriority(taskRequestDTO.getPriority());

            taskRepository.save(task);

            commonResponseDTO.setSuccess(true);
            commonResponseDTO.setMessage("Task updated successfully!");
            return new ResponseEntity<>(commonResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            commonResponseDTO.setSuccess(false);
            commonResponseDTO.setMessage("Unable to update task with provided taskId!");
            return new ResponseEntity<>(commonResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
