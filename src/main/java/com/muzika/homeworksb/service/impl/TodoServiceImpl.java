package com.muzika.homeworksb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final TodoMapper todoMapper;
    private final TaskHistoryMapper historyMapper;

    @Autowired
    private final ObjectMapper objectMapper;

    @Override
    public TodoResponseDto save(TodoCreateDto createDto) {

        Todo todo = todoMapper.toModel(createDto);
        // todo.setCreatedDate(LocalDateTime.now()); instead this row added annotation @CreationTimestamp

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
            stringifiedOld = objectMapper.writeValueAsString(todo);
        } catch (JsonProcessingException e) {
            log.warn("Impossible to jsonize todo instance");
            throw new RuntimeException("Impossible to jsonize old state");
        }

        todo.setTitle(updateDto.title());
        todo.setDescription(updateDto.description());
        todo.setDueDate(updateDto.dueDate());
        todo.setPriority(updateDto.priority());
        todo.setStatus(updateDto.status());
        todo.setUpdatedDate(LocalDateTime.now());

        try {
            stringifiedNew = objectMapper.writeValueAsString(todo);
        } catch (JsonProcessingException e) {
            log.warn("Impossible to jsonize todo instance");
            stringifiedNew = todo.toString();
        }

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(todo);
        taskHistory.setOldState(stringifiedOld);
        taskHistory.setNewState(stringifiedNew);
        //taskHistory.setChangedBy(); // tODO in 6th HW

        taskHistoryRepository.save(taskHistory);

        return todoMapper.toDto(
            todoRepository.save(
                todo
            )
        );
    }

    @Override
    public List<TaskHistoryResponseDto> findHistoryById(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found todo by id: " + id);
        }

        return todoRepository.findHistoryById(id).stream()
            .map(history -> {
                TaskHistoryResponseDto taskHistoryResponseDto = historyMapper.toDto(history);
                return taskHistoryResponseDto; //.setTodoId(id); unnessesary, because I added mapping in TaskHistoryMapper
            })
            .toList();
    }
}
