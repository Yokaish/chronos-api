package br.com.chronos.controller;


import br.com.chronos.domain.task.TaskDataDTO;
import br.com.chronos.repository.TaskRepository;
import br.com.chronos.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
