package com.choma.kanbanapi.controller;

import com.choma.kanbanapi.model.Task;
import com.choma.kanbanapi.model.TaskPriority;
import com.choma.kanbanapi.model.TaskStatus;
import com.choma.kanbanapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(taskRepository.findByStatusOrderByPriorityAsc(TaskStatus.TODO));
        tasks.addAll(taskRepository.findByStatusOrderByPriorityAsc(TaskStatus.IN_PROGRESS));
        tasks.addAll(taskRepository.findByStatusOrderByPriorityAsc(TaskStatus.DONE));
        return tasks;
    }

    @PutMapping("/{id}/move")
    public ResponseEntity<Task> moveTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();
        if (task.getStatus() == TaskStatus.TODO) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        } else if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            task.setStatus(TaskStatus.DONE);
        } else {
            return ResponseEntity.badRequest().build();
        }
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());
        taskRepository.save(task);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
