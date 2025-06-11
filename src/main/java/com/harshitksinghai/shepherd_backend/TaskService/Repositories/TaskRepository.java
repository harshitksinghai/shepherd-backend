package com.harshitksinghai.shepherd_backend.TaskService.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harshitksinghai.shepherd_backend.TaskService.Models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

    void deleteByTaskId(String taskId);

    Optional<Task> findByTaskId(String taskId);
    
}
