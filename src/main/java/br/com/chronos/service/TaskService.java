package br.com.chronos.service;

import br.com.chronos.domain.task.TaskDataDTO;
import br.com.chronos.domain.task.TaskEntity;
import br.com.chronos.domain.task.TaskResponseDTO;
import br.com.chronos.domain.task.TaskStatus;
import br.com.chronos.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;

    private Page<TaskResponseDTO> findByStatus(TaskStatus status, Pageable pagination) {
        return repository.findAllByStatus(status, pagination)
                .map(TaskResponseDTO::new);
    }

    public ResponseEntity createTask(TaskDataDTO taskData, UriComponentsBuilder uriBuilder) {

        var task = new TaskEntity(taskData);
        repository.save(task);

        var uri = uriBuilder.buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(taskData);

    }


    public ResponseEntity listTasksByStatus(TaskStatus status, Pageable pagination) {
        Page<TaskResponseDTO> tasks = findByStatus(status, pagination);
        return ResponseEntity.ok(tasks);
    }


    public ResponseEntity listAllTasks() {
        List<TaskEntity> tasks = repository.findAll();

        Map<String, List<TaskResponseDTO>> groupedTasks =
                tasks.stream().map(TaskResponseDTO::new).
                        collect(Collectors.groupingBy(t ->
                                t.status().equals(TaskStatus.PENDING) ? "Pending" :
                                t.status().equals(TaskStatus.IN_PROGRESS) ? "In progress" :
                                        "Completed"
                        ));

        return ResponseEntity.ok(groupedTasks);
    }
}
