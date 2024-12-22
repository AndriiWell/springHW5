package com.muzika.homeworksb.repository;

import com.muzika.homeworksb.model.TaskHistory;
import com.muzika.homeworksb.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo save(Todo todo);

    Optional<Todo> findById(Long id);

    @Query("Select t from TaskHistory t where t.todo.id=:todoId")
    List<TaskHistory> findHistoryById(@Param("todoId") Long id);
}
