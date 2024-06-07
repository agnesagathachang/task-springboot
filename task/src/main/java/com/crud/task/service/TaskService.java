package com.crud.task.service;

import com.crud.task.entity.Task;
import com.crud.task.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

  @Autowired
  private final TaskRepo taskRepo;

  public TaskService(TaskRepo taskRepo) {
    this.taskRepo = taskRepo;
  }

  public Task saveTask(Task task) {
    return taskRepo.save(task);
  }

  public List<Task> getAllTasks() {
    return taskRepo.findAll();
  }

  public Optional<Task> getTaskById(Long id) {
    return taskRepo.findById(id);
  }

  public List<Task> getTasksByStatus(String status) {
    return taskRepo.findByStatus(status);
  }

  public Task updateTask(Long id, Task updatedTask) {
    Optional<Task> existingTask = taskRepo.findById(id);
    if (existingTask.isPresent()) {
      Task task = existingTask.get();
      task.setName(updatedTask.getName());
      task.setDescription(updatedTask.getDescription());
      return taskRepo.save(task);
    } else {
      throw new RuntimeException("Task not found");
    }
  }

  public void deleteTask(Long id) {
    taskRepo.deleteById(id);
  }
}