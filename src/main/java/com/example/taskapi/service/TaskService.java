package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import com.example.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // get all task
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // get task by ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // create new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // update existing tasks
    public Task updateTask(Long id, Task updateTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null) {
            return null; // task not found
        }
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setCompleted(updateTask.getCompleted());

        return taskRepository.save(existingTask);
    }

    // delete task
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
