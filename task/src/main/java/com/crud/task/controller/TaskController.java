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

  @PostMapping("/addTask")
  public ResponseEntity<Task> saveTask(@RequestBody Task task) {
    Task newTask = taskService.saveTask(task);
    return ResponseEntity.ok(newTask);
  }

  @GetMapping("/tasks")
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/test")
  public String test() {
    return "halo";
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    Optional<Task> task = taskService.getTaskById(id);
    return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/tasks/status/{status}")
  public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
    List<Task> tasks = taskService.getTasksByStatus(status);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
    Task updatedTask = taskService.updateTask(id, task);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<String> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok("Task deleted successfully");
  }
}