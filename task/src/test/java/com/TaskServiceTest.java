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
  public void testGetAllTasksEmpty() {
    when(taskRepo.findAll()).thenReturn(new ArrayList<>());

    assertThrows(CustomErrorExceptionHandler.class, () -> {
      taskService.getAllTasks();
    });

    verify(taskRepo, times(1)).findAll();
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
  public void testGetTaskByIdNotFound() {
    long id = 1L;
    when(taskRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(CustomErrorExceptionHandler.class, () -> {
      taskService.getTaskById(id);
    });

    verify(taskRepo, times(1)).findById(id);
  }

}
