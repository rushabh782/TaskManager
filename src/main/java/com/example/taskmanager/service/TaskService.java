package com.example.taskmanager.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    // Get task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    // Get tasks by completion status
    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
    
    // Search tasks by title
    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContaining(title);
    }
    
    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    // Update an existing task
    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
    
    // Delete a task
    public void deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
    
    // Toggle task completion status
    public Task toggleTaskCompletion(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(!task.isCompleted());
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
}