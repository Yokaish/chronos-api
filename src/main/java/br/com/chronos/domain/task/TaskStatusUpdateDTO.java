package br.com.chronos.domain.task;

import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateDTO(
        @NotNull TaskStatus status
) {
}
