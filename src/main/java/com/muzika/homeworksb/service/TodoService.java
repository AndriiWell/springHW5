package com.muzika.homeworksb.service;

import com.muzika.homeworksb.dto.TaskHistoryResponseDto;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.dto.TodoUpdateDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto save(TodoCreateDto createDto);

    TodoResponseDto save(String email, TodoCreateDto createDto);

    TodoResponseDto findById(Long id);

    List<TodoResponseDto> findAll();

    void deleteById(Long id);

    TodoResponseDto update(Long id, TodoUpdateDto updateDto);

    TodoResponseDto update(String email, Long id, TodoUpdateDto updateDto);

    List<TaskHistoryResponseDto> findHistoryById(Long id);

    List<TaskHistoryResponseDto> findHistoryById(String email, Long id);
}
