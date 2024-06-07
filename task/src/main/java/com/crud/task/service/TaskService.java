package com.crud.task.service;

import com.crud.task.entity.Task;
import com.crud.task.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Task entities.
 */
@Service
public class TaskService {

  @Autowired
  private final TaskRepo taskRepo;

  public TaskService(TaskRepo taskRepo) {
    this.taskRepo = taskRepo;
  }

  /**
   * Save a task.
   *
   * @param task the entity to save
   * @return the persisted entity
   */
  public Task saveTask(Task task) {
    return taskRepo.save(task);
  }

  /**
   * Get all the tasks.
   *
   * @return the list of entities
   */
  public List<Task> getAllTasks() {
    return taskRepo.findAll();
  }

  /**
   * Get one task by ID.
   *
   * @param id the ID of the entity
   * @return the entity
   */
  public Optional<Task> getTaskById(Long id) {
    return taskRepo.findById(id);
  }

  /**
   * Update a task.
   *
   * @param id          the ID of the entity
   * @param updatedTask the updated entity
   * @return the updated entity
   */
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

  /**
   * Delete the task by ID.
   *
   * @param id the ID of the entity
   */
  public void deleteTask(Long id) {
    taskRepo.deleteById(id);
  }
}