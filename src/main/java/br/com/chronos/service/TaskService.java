package br.com.chronos.service;

import br.com.chronos.domain.task.TaskDataDTO;
import br.com.chronos.domain.task.TaskEntity;
import br.com.chronos.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;

    public ResponseEntity createTask(TaskDataDTO taskData, UriComponentsBuilder uriBuilder) {


        var task = new TaskEntity(taskData);
        repository.save(task);

        var uri = uriBuilder.buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(taskData);

    }


}
