package com.harshitksinghai.shepherd_backend.TaskService.DTOs.ResponseDTO;

import lombok.Data;

@Data
public class CommonResponseDTO {
    private boolean success;
    private String message;
    private String util;
}

