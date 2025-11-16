package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    // in memory storage
    private List<Task> tasks = new ArrayList<>();

    // auto-incrementing ID
    private AtomicLong idCounter = new AtomicLong(1);

    // get all task
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // Return copy to avoid external modification
    }

    // get task by ID
    public Task getTaskById(Long id) {
        for (Task task: tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    // create new task
    public Task createTask(Task task) {
        task.setId(idCounter.getAndIncrement());
        tasks.add(task);
        return task;
    }

    // update existing tasks
    public Task updateTask(Long id, Task updateTask) {
        Task existingTask = getTaskById(id);
        if (existingTask == null) {
            return null; // task not found
        }
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setCompleted(updateTask.getCompleted());

        return existingTask;
    }

    // delete task
    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }



}
