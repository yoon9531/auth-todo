package com.example.authtodo.controller;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.User;
import com.example.authtodo.entity.dto.TaskCreateDTO;
import com.example.authtodo.entity.dto.TaskRequestDTO;
import com.example.authtodo.entity.dto.TaskResponseDTO;
import com.example.authtodo.entity.dto.UserResponseDTO;
import com.example.authtodo.global.ApiResponse;
import com.example.authtodo.security.JwtUtil;
import com.example.authtodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));
        List<Task> allTasks = taskService.getAllTasks(username);

        List<TaskResponseDTO> result = allTasks.stream()
                .map(task -> TaskResponseDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .user(new UserResponseDTO(task.getUser().getId(), task.getUser().getUsername()))
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestHeader("Authorization") String token,
                                                                   @RequestBody TaskCreateDTO taskCreateDTO) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));

        Task task = taskService.createTask(username, taskCreateDTO);

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .user(new UserResponseDTO(task.getUser().getId(), task.getUser().getUsername()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponseDTO);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@RequestHeader("Authorization") String token,
                                                      @PathVariable Long taskId,
                                                      @RequestBody TaskRequestDTO taskRequestDTO) {

        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));

        Task updatedTask = taskService.updateTask(username, taskId, taskRequestDTO.getTitle(), taskRequestDTO.getDescription());

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .user(new UserResponseDTO(updatedTask.getUser().getId(), updatedTask.getUser().getUsername()))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(taskResponseDTO);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long taskId) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));

        taskService.deleteTask(username, taskId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task deleted successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTasks(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.validateTokenAndGetUsername(token.replace("Bearer ", ""));

        taskService.deleteAllTasks(username);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All tasks deleted successfully");
    }

}
