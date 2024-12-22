package com.muzika.homeworksb.dto;

import java.time.Instant;

public record TaskHistoryResponseDto(
    Long id,
    Long todoId,
    String oldState,
    String newState,
    Instant changeDate,
    String changedBy
) {
    // Unnecessary, because was added mapping in TaskHistoryMapper.
//    public TaskHistoryResponseDto setTodoId(Long todosId) {
//        return new TaskHistoryResponseDto(
//            this.id,
//            todosId,
//            this.oldState,
//            this.newState,
//            this.changeDate,
//            this.changedBy
//        );
//    }
}
