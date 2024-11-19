package com.choma.kanbanapi.repository;

import com.choma.kanbanapi.model.Task;
import com.choma.kanbanapi.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.status = :status ORDER BY " +
            "CASE WHEN t.priority = 'HIGH' THEN 1 " +
            "WHEN t.priority = 'MEDIUM' THEN 2 " +
            "WHEN t.priority = 'LOW' THEN 3 END ASC")
    List<Task> findByStatusOrderByPriorityAsc(TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.id = :id ORDER BY " +
            "CASE WHEN t.priority = 'HIGH' THEN 1 " +
            "WHEN t.priority = 'MEDIUM' THEN 2 " +
            "WHEN t.priority = 'LOW' THEN 3 END ASC")
    List<Task> findByStatusAndIdOrderByPriorityAsc(TaskStatus status, Long id);
}
