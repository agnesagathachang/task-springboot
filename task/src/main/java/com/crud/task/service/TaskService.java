package com.crud.task.service;

import com.crud.task.entity.Task;
import com.crud.task.exception.CustomErrorExceptionHandler;
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
    List<Task> allTasks = taskRepo.findAll();
    if (allTasks.isEmpty()) {
      throw new CustomErrorExceptionHandler("No tasks found.");
    }
    return allTasks;
  }

  public Optional<Task> getTaskById(Long id) {
    Optional<Task> foundTask = taskRepo.findById(id);
    if (foundTask.isEmpty()) {
      throw new CustomErrorExceptionHandler("No tasks with id " + id + " found.");
    }
    return foundTask;
  }

  public List<Task> getTasksByStatus(String status) {
    List<Task> foundTask = taskRepo.findByStatus(status);
    if (foundTask.isEmpty()) {
      throw new CustomErrorExceptionHandler("No tasks with status " + status + " found.");
    }
    return foundTask;
  }

  public String updateTask(Long id, Task task) {
    Task existingTask = taskRepo.findById(id)
        .orElse(null);
    if (existingTask == null)
      throw new CustomErrorExceptionHandler(
          "No such task exists!");
    else {
      existingTask.setName(task.getName());
      existingTask.setDescription(
          task.getDescription());
      existingTask.setName(task.getName());
      existingTask.setStatus(
          task.getStatus());
      taskRepo.save(existingTask);
      return "Update success!";
    }
  }

  public String updateTaskStatus(Long id, String status) {
    Optional<Task> optionalTask = taskRepo.findById(id);
    if (optionalTask.isPresent()) {
      Task existingTask = optionalTask.get();
      existingTask.setStatus(status);
      taskRepo.save(existingTask);
      return "Update success!";
    } else {
      throw new CustomErrorExceptionHandler("No such task exists!");
    }
  }

  public String deleteTask(Long id) {
    Task existingTask = taskRepo.findById(id)
        .orElse(null);
    if (existingTask == null)
      throw new CustomErrorExceptionHandler(
          "No such task exists!");
    else {
      taskRepo.deleteById(id);
      return "Delete success!";
    }
  }
}