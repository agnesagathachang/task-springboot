package com;

import com.crud.task.entity.Task;
import com.crud.task.exception.CustomErrorExceptionHandler;
import com.crud.task.repo.TaskRepo;
import com.crud.task.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

  @Mock
  private TaskRepo taskRepo;

  @InjectMocks
  private TaskService taskService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testSaveTask() {
    Task task = new Task("Task 1", "Description", "To Do");
    when(taskRepo.save(task)).thenReturn(task);

    Task savedTask = taskService.saveTask(task);

    assertNotNull(savedTask);
    assertEquals(task, savedTask);

    verify(taskRepo, times(1)).save(task);
  }

  @Test
  public void testGetAllTasks() {
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task("Task 1", "Description 1", "To Do"));
    tasks.add(new Task("Task 2", "Description 2", "In Progress"));

    when(taskRepo.findAll()).thenReturn(tasks);

    List<Task> allTasks = taskService.getAllTasks();

    assertNotNull(allTasks);
    assertEquals(2, allTasks.size());

    verify(taskRepo, times(1)).findAll();
  }

  @Test
  public void testUpdateTask() {
    long id = 1L;
    Task existingTask = new Task("Existing Task", "Existing Description", "In Progress");
    Task updateValue = new Task("Updated Task", "Updated Description", "Completed");

    when(taskRepo.findById(id)).thenReturn(Optional.of(existingTask));
    when(taskRepo.save(any(Task.class))).thenReturn(updateValue);

    Task foundTask = taskService.getTaskById(id).orElse(null);
    assertNotNull(foundTask);

    String result = taskService.updateTask(id, updateValue);

    assertEquals("Update success!", result);

    verify(taskRepo, times(2)).findById(id);
    verify(taskRepo, times(1)).save(existingTask);

    assertEquals(updateValue.getName(), existingTask.getName());
  }

  @Test
  public void testGetTaskById() {
    long id = 1L;
    Task task = new Task("Task 1", "Description", "To Do");
    when(taskRepo.findById(id)).thenReturn(Optional.of(task));

    Task foundTask = taskService.getTaskById(id).orElse(null);

    assertNotNull(foundTask);
    assertEquals(task, foundTask);

    verify(taskRepo, times(1)).findById(id);
  }

  @Test
  public void testDeleteTaskSuccess() {
    long id = 1L;
    Task existingTask = new Task("Task 1", "Description", "To Do");

    when(taskRepo.findById(id)).thenReturn(Optional.of(existingTask));

    String result = taskService.deleteTask(id);

    assertEquals("Delete success!", result);

    verify(taskRepo, times(1)).findById(id);
    verify(taskRepo, times(1)).deleteById(id);
  }

  @Test
  public void testDeleteTaskNotFound() {
    long id = 1L;

    when(taskRepo.findById(id)).thenReturn(Optional.empty());

    CustomErrorExceptionHandler exception = assertThrows(CustomErrorExceptionHandler.class, () -> {
      taskService.deleteTask(id);
    });

    assertEquals("No such task exists!", exception.getMessage());

    verify(taskRepo, times(1)).findById(id);
    verify(taskRepo, times(0)).deleteById(id);
  }
}
