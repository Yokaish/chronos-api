package br.com.chronos.repository;

import br.com.chronos.domain.task.TaskEntity;
import br.com.chronos.domain.task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


    Page<TaskEntity> findAllByStatus(TaskStatus status, Pageable pageable);

}
