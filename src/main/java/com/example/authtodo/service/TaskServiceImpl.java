package com.example.authtodo.service;

import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.User;
import com.example.authtodo.entity.dto.TaskRequestDTO;
import com.example.authtodo.exception.handler.TaskExceptionHandler;
import com.example.authtodo.exception.handler.UserExceptionHandler;
import com.example.authtodo.global.ErrorStatus;
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
    public Task createTask(String username, TaskRequestDTO taskRequestDTO) {

        User user = findUserOrThrow(username);

        Task task = Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(String username, Long taskId, TaskRequestDTO taskRequestDTO) {
        User user = findUserOrThrow(username);
        Task task = findTaskorThrow(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskExceptionHandler(ErrorStatus.TASK_NOT_AUTHORIZED);
        }

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String username, Long taskId) {
        User user = findUserOrThrow(username);
        Task task = findTaskorThrow(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskExceptionHandler(ErrorStatus.TASK_NOT_AUTHORIZED);
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
            throw new TaskExceptionHandler(ErrorStatus.TASK_NOT_AUTHORIZED);
        }

        taskRepository.findByIdAndUserId(task.getId(), user.getId());

        return task;
    }

    private User findUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserExceptionHandler(ErrorStatus.USER_NOT_FOUND));
    }

    private Task findTaskorThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskExceptionHandler(ErrorStatus.TASK_NOT_FOUND));
    }
}
