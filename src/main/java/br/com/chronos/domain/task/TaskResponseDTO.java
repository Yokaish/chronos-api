package br.com.chronos.domain.task;

import java.time.ZonedDateTime;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        ZonedDateTime createdAt,
        ZonedDateTime dueDate
) {
    public TaskResponseDTO(TaskEntity task) {
        this(task.getId(),task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(),task.getCreatedAt(), task.getDueDate());
    }
}
