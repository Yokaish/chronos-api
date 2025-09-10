package br.com.chronos.domain.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tasks")
@Entity(name = "Task")
public class TaskEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

    @Column
    private ZonedDateTime dueDate;

    @Column
    private ZonedDateTime completedAt;

    public TaskEntity(TaskDataDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.status = TaskStatus.PENDING;
        this.priority = data.priority();
        if (data.dueDate() != null) {
            var parsedDueDate = DataParser.parseSimpleData(data.dueDate());
            if (parsedDueDate.isBefore(this.createdAt)) {
                throw new IllegalArgumentException("Due date must be equal or later than createdAt");
            }
            this.dueDate = parsedDueDate;
        }
    }

    public void markAsInProgress() {
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void markAsCompleted() {
        this.status = TaskStatus.COMPLETED;
        this.completedAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }


}
