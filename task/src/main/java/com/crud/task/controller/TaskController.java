package com.crud.task.controller;

import com.crud.task.entity.Task;
import com.crud.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class TaskController {

  @Autowired
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  // Create a New Task
  @PostMapping("/createNewTask")
  public ResponseEntity<Task> saveTask(@RequestBody Task task) {
    Task newTask = taskService.saveTask(task);
    return ResponseEntity.ok(newTask);
  }

  // Retrieve All Tasks
  @GetMapping("/retrieveTasks")
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  // Retrieve Task by Id
  @GetMapping("/retrieveTasks/id/{id}")
  public Optional<Task> getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
  }

  // Retrieve Tasks by Status
  @GetMapping("/retrieveTasks/status/{status}")
  public List<Task> getTasksByStatus(@PathVariable String status) {
    return taskService.getTasksByStatus(status);
  }

  // Update Task by Id
  @PutMapping("/updateTask/{id}")
  public String updateTask2(@PathVariable Long id, @RequestBody Task task) {
    return taskService.updateTask(id, task);
  }

  // Delete Task by Id
  @DeleteMapping("/deleteTask/{id}")
  public String deleteTask(@PathVariable Long id) {
    return taskService.deleteTask(id);
  }
}