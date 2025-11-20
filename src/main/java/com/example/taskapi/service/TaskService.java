package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import com.example.taskapi.model.User;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Get current logged-in user
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all tasks for current user
    public List<Task> getAllTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findAll().stream()
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    // Get task by ID (only if belongs to current user)
    public Task getTaskById(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id).orElse(null);

        if (task != null && task.getUser().getId().equals(currentUser.getId())) {
            return task;
        }
        return null;
    }

    // Create new task for current user
    public Task createTask(Task task) {
        User currentUser = getCurrentUser();
        task.setUser(currentUser);  // Set owner
        return taskRepository.save(task);
    }

    // Update task (only if belongs to current user)
    public Task updateTask(Long id, Task updatedTask) {
        User currentUser = getCurrentUser();
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask == null || !existingTask.getUser().getId().equals(currentUser.getId())) {
            return null;
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }

    // Delete task (only if belongs to current user)
    public boolean deleteTask(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id).orElse(null);

        if (task != null && task.getUser().getId().equals(currentUser.getId())) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}