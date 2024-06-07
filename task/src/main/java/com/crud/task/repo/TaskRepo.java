package com.crud.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.crud.task.entity.Task;

@RepositoryRestResource
public interface TaskRepo extends JpaRepository<Task, Long> {
  List<Task> findByStatus(String status);
}