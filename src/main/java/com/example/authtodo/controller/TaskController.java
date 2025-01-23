package com.example.authtodo.controller;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.User;
import com.example.authtodo.entity.dto.TaskCreateDTO;
import com.example.authtodo.entity.dto.TaskResponseDTO;
import com.example.authtodo.entity.dto.UserResponseDTO;
import com.example.authtodo.security.JwtUtil;
import com.example.authtodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<Task> getTasks(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));
        return taskService.getAllTasks(username);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestHeader("Authorization") String token,
                                                      @RequestBody TaskCreateDTO taskCreateDTO) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));

        Task task = taskService.createTask(username, taskCreateDTO);

        User user = task.getUser();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .user(new UserResponseDTO(user.getId(), user.getUsername()))
                .build();


        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
    }
}
