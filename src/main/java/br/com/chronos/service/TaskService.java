package br.com.chronos.service;

import br.com.chronos.domain.task.*;
import br.com.chronos.repository.TaskRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @Transactional
    public ResponseEntity<TaskResponseDTO> updateTask(Long id, TaskStatusUpdateDTO statusUpdate) {

        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        switch (statusUpdate.status()) {
            case PENDING -> task.markAsPending();
            case IN_PROGRESS -> task.markAsInProgress();
            case COMPLETED -> task.markAsCompleted();
        }

        repository.save(task);

        return ResponseEntity.ok(new TaskResponseDTO(task));
    }

}
