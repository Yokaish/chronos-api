package br.com.chronos.controller;


import br.com.chronos.domain.task.TaskDataDTO;
import br.com.chronos.domain.task.TaskStatus;
import br.com.chronos.domain.task.TaskStatusUpdateDTO;
import br.com.chronos.repository.TaskRepository;
import br.com.chronos.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody @Valid TaskDataDTO taskData, UriComponentsBuilder uriBuilder) {
        return service.createTask(taskData, uriBuilder);
    }

    @GetMapping()
    public ResponseEntity listTasks(@RequestParam TaskStatus status, @PageableDefault(size = 10, sort = "createdAt") Pageable pagination) {
        return service.listTasksByStatus(status, pagination);
    }

    @GetMapping("/all")
    public ResponseEntity listAllTasks() {
        return service.listAllTasks();
    }

    @PutMapping("update/{id}/status")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody @Valid TaskStatusUpdateDTO status) {
        return service.updateTask(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        return service.deleteTask(id);
    }

}
