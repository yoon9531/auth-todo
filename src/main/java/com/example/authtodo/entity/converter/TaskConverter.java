package com.example.authtodo.entity.converter;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.dto.TaskRequestDTO;
import com.example.authtodo.entity.dto.TaskResponseDTO;
import com.example.authtodo.entity.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {


    public static TaskRequestDTO toTaskRequestDTO(Task task) {
        return TaskRequestDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .build();
    }

    public static TaskResponseDTO toTaskResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .user(UserResponseDTO.builder()
                        .id(task.getUser().getId())
                        .username(task.getUser().getUsername())
                        .build())
                .build();
    }

}
