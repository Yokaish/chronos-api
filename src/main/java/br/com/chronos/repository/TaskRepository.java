package br.com.chronos.repository;

import br.com.chronos.domain.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {



}
