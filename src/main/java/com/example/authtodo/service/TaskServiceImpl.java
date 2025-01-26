package com.example.authtodo.service;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.User;
import com.example.authtodo.entity.dto.TaskCreateDTO;
import com.example.authtodo.repository.TaskRepository;
import com.example.authtodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Task createTask(String username, TaskCreateDTO taskCreateDTO) {

        User user = findUserOrThrow(username);

        Task task = Task.builder()
                .title(taskCreateDTO.getTitle())
                .description(taskCreateDTO.getDescription())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(String username, Long taskId, String title, String description) {
        User user = findUserOrThrow(username);
        Task task = findTaskorThrow(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Task not found");
        }

        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String username, Long taskId) {
        User user = findUserOrThrow(username);
        Task task = findTaskorThrow(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Task not found");
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteAllTasks(String username) {
        findUserOrThrow(username);

        taskRepository.deleteAllByUserUsername(username);
    }

    @Override
    public List<Task> getAllTasks(String username) {

        User user = findUserOrThrow(username);

        return taskRepository.findByUserId(user.getId());
    }

    @Override
    public Task getTaskByUserId(String username, Long taskId) {

        User user = findUserOrThrow(username);
        Task task = findTaskorThrow(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Task not found");
        }

        taskRepository.findByIdAndUserId(task.getId(), user.getId());

        return task;
    }

    private User findUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Task findTaskorThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }
}
