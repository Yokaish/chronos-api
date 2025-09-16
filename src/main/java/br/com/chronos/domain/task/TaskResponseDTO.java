package br.com.chronos.domain.task;

import java.time.ZonedDateTime;

public record TaskResponseDTO(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        ZonedDateTime createdAt,
        ZonedDateTime dueDate
) {
    public TaskResponseDTO(TaskEntity task) {
        this(task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(),task.getCreatedAt(), task.getDueDate());
    }
}
