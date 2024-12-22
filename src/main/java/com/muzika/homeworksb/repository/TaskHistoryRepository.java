package com.muzika.homeworksb.repository;

import com.muzika.homeworksb.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    TaskHistory save(TaskHistory taskHistory);
}
