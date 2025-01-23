package com.example.authtodo.service;


import com.example.authtodo.entity.Task;
import com.example.authtodo.entity.dto.TaskCreateDTO;

import java.util.List;

public interface TaskService {

    Task createTask(String username, TaskCreateDTO taskCreateDTO);
    Task updateTask(String username, Long taskId, String title, String description);
    void deleteTask(String username, Long taskId);
    void deleteAllTasks(String username);
    List<Task> getAllTasks(String username);
    Task getTaskByUserId(String username, Long taskId);

}
