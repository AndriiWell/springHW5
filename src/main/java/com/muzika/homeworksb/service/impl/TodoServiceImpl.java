package com.muzika.homeworksb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.muzika.homeworksb.dto.TaskHistoryResponseDto;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.dto.TodoUpdateDto;
import com.muzika.homeworksb.exception.EntityNotFoundException;
import com.muzika.homeworksb.mapper.TaskHistoryMapper;
import com.muzika.homeworksb.mapper.TodoMapper;
import com.muzika.homeworksb.model.TaskHistory;
import com.muzika.homeworksb.model.Todo;
import com.muzika.homeworksb.repository.TaskHistoryRepository;
import com.muzika.homeworksb.repository.TodoRepository;
import com.muzika.homeworksb.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final TodoMapper todoMapper;
    private final TaskHistoryMapper historyMapper;

    @Override
    public TodoResponseDto save(TodoCreateDto createDto) {

        Todo todo = todoMapper.toModel(createDto);
        todo.setCreatedDate(LocalDateTime.now());

        return todoMapper.toDto(
            todoRepository.save(
                todo
            )
        );
    }

    @Override
    public TodoResponseDto findById(Long id) {
        return todoRepository.findById(id)
            .map(todoMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Not found todo by id: " + id));
    }

    @Override
    public List<TodoResponseDto> findAll() {
        return todoRepository.findAll().stream()
            .map(todoMapper::toDto)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return;
        }

        throw new EntityNotFoundException("Todo with id:" + id + " was not found");
    }

    @Transactional(rollbackFor = Exception.class, timeout = 1)
    public TodoResponseDto update(Long id, TodoUpdateDto updateDto) {

        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("No todo to update by id: " + id));

        String stringifiedOld;
        String stringifiedNew;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            stringifiedOld = objectMapper.writeValueAsString(todo);
        } catch (JsonProcessingException e) {
            System.err.println("Impossible to jsonize todo instance");
            stringifiedOld = todo.toString();
        }

        todo.setTitle(updateDto.title());
        todo.setDescription(updateDto.description());
        todo.setDueDate(updateDto.dueDate());
        todo.setPriority(updateDto.priority());
        todo.setStatus(updateDto.status());
        todo.setUpdatedDate(LocalDateTime.now());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            stringifiedNew = objectMapper.writeValueAsString(todo);
        } catch (JsonProcessingException e) {
            System.err.println("Impossible to jsonize todo instance");
            stringifiedNew = todo.toString();
        }

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(todo);
        taskHistory.setOldState(stringifiedOld);
        taskHistory.setNewState(stringifiedNew);
        taskHistory.setChangeDate(LocalDateTime.now());
        //taskHistory.setChangedBy();

        taskHistoryRepository.save(taskHistory);

        return todoMapper.toDto(
            todoRepository.save(
                todo
            )
        );
    }

    @Override
    public List<TaskHistoryResponseDto> findHistoryById(Long id) {
        return todoRepository.findHistoryById(id).stream()
            .map(history -> {
                TaskHistoryResponseDto taskHistoryResponseDto = historyMapper.toDto(history);
                return taskHistoryResponseDto.setTodoId(id);
            })
            .toList();
    }
}
