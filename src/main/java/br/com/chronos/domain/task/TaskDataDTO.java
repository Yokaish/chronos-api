package br.com.chronos.domain.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskDataDTO(

        @NotBlank @Size(max = 255)
        String title,

        @Size(max = 255)
        String description,

        @NotNull
        TaskPriority priority,

        String dueDate
) {

}
