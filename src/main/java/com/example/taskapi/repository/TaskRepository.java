package com.example.taskapi.repository;

import com.example.taskapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // That's it! JPA provides all CRUD methods automatically
    // we provide it the entity type(task) and id type(Long)
}