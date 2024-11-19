package com.choma.kanbanapi.repository;

import com.choma.kanbanapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

