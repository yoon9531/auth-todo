package com.example.authtodo.service;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.User;
import com.example.authtodo.entity.dto.TaskCreateDTO;
import com.example.authtodo.repository.TaskRepository;
import com.example.authtodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Task createTask(String username, TaskCreateDTO taskCreateDTO) {

        if (!isUserExist(username)) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userRepository.findByUsername(username).get();

        Task task = Task.builder()
                .title(taskCreateDTO.getTitle())
                .description(taskCreateDTO.getDescription())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(String username, Long taskId, String title, String description) {
        return null;
    }

    @Override
    public void deleteTask(String username, Long taskId) {
        if (!isUserExist(username)) {
            throw new IllegalArgumentException("User not found");
        }

        if (!isTaskExist(taskId)) {
            throw new IllegalArgumentException("Task not found");
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteAllTasks(String username) {
        if (!isUserExist(username)) {
            throw new IllegalArgumentException("User not found");
        }

        taskRepository.deleteAllByUserUsername(username);
    }

    @Override
    public List<Task> getAllTasks(String username) {

        if (!isUserExist(username)) {
            throw new IllegalArgumentException("User not found");
        }

        return taskRepository.findByUserId(userRepository.findByUsername(username).get().getId());
    }

    @Override
    public Task getTaskByUserId(String username, Long taskId) {

        if (!isUserExist(username)) {
            throw new IllegalArgumentException("User not found");
        }

        if (!isTaskExist(taskId)) {
            throw new IllegalArgumentException("Task not found");
        }

        Long userId = userRepository.findByUsername(username).get().getId();

        return taskRepository.findByIdAndUserId(taskId, userId).get();
    }

    private boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean isTaskExist(Long taskId) {
        return taskRepository.existsById(taskId);
    }
}
