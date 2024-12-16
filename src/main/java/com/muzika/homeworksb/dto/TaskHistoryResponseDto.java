package com.muzika.homeworksb.dto;

import java.time.LocalDateTime;

public record TaskHistoryResponseDto(
    Long id,
    Long todoId,
    String oldState,
    String newState,
    LocalDateTime changeDate,
    String changedBy
) {
    public TaskHistoryResponseDto setTodoId(Long todosId) {
        return new TaskHistoryResponseDto(
            this.id,
            todosId,
            this.oldState,
            this.newState,
            this.changeDate,
            this.changedBy
        );
    }
}
