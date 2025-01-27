package com.example.authtodo.service;


import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.dto.TaskRequestDTO;

import java.util.List;

public interface TaskService {

    Task createTask(String username, TaskRequestDTO taskRequestDTO);
    Task updateTask(String username, Long taskId, TaskRequestDTO taskRequestDTO);
    void deleteTask(String username, Long taskId);
    void deleteAllTasks(String username);
    List<Task> getAllTasks(String username);
    Task getTaskByUserId(String username, Long taskId);

}
