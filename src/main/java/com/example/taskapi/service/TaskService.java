package com.example.taskapi.service;

import com.example.taskapi.exception.ResourceNotFoundException;
import com.example.taskapi.model.Task;
import com.example.taskapi.model.User;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class for managing Task operations.
 * Handles business logic for CRUD operations on tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets the currently authenticated user from the security context.
     *
     * @return Current authenticated user
     * @throws ResourceNotFoundException if user not found
     */
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    /**
     * Retrieves all tasks belonging to the current user.
     *
     * @return List of tasks for current user
     */
    public List<Task> getAllTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findAll().stream()
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific task by ID.
     * Verifies that the task belongs to the current user.
     *
     * @param id Task ID
     * @return Task object
     * @throws ResourceNotFoundException if task not found or doesn't belong to user
     */
    public Task getTaskById(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

        // Check if task belongs to current user
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Task", id);
        }

        return task;
    }

    /**
     * Creates a new task for the current user.
     *
     * @param task Task object to create
     * @return Created task
     */
    public Task createTask(Task task) {
        User currentUser = getCurrentUser();
        task.setUser(currentUser);
        return taskRepository.save(task);
    }

    /**
     * Updates an existing task.
     * Verifies that the task belongs to the current user.
     *
     * @param id Task ID to update
     * @param updatedTask Updated task data
     * @return Updated task
     * @throws ResourceNotFoundException if task not found or doesn't belong to user
     */
    public Task updateTask(Long id, Task updatedTask) {
        User currentUser = getCurrentUser();
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

        // Check ownership
        if (!existingTask.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Task", id);
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }

    /**
     * Deletes a task by ID.
     * Verifies that the task belongs to the current user.
     *
     * @param id Task ID to delete
     * @throws ResourceNotFoundException if task not found or doesn't belong to user
     */
    public void deleteTask(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

        // Check ownership
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Task", id);
        }

        taskRepository.deleteById(id);
    }
}