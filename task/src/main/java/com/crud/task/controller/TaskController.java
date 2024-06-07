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

  /**
   * Create a new task.
   *
   * @param task the task to create
   * @return the ResponseEntity with status 200 (OK) and with body of the new task
   */
  @PostMapping("/addTask")
  public ResponseEntity<Task> saveTask(@RequestBody Task task) {
    Task newTask = taskService.saveTask(task);
    return ResponseEntity.ok(newTask);
  }

  /**
   * Get all tasks.
   *
   * @return the ResponseEntity with status 200 (OK) and with body of the list of
   *         tasks
   */
  @GetMapping("/tasks")
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/test")
  public String test() {
    return "halo";
  }

  /**
   * Get a task by ID.
   *
   * @param id the ID of the task to get
   * @return the ResponseEntity with status 200 (OK) and with body of the task, or
   *         with status 404 (Not Found) if the task does not exist
   */
  @GetMapping("/tasks/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    Optional<Task> task = taskService.getTaskById(id);
    return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Update a task by ID.
   *
   * @param id   the ID of the task to update
   * @param task the updated task
   * @return the ResponseEntity with status 200 (OK) and with body of the updated
   *         task, or with status 404 (Not Found) if the task does not exist
   */
  @PutMapping("/tasks/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
    Task updatedTask = taskService.updateTask(id, task);
    return ResponseEntity.ok(updatedTask);
  }

  /**
   * Delete a task by ID.
   *
   * @param id the ID of the task to delete
   * @return the ResponseEntity with status 200 (OK) and with body of the message
   *         "Task deleted successfully"
   */
  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<String> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok("Task deleted successfully");
  }
}