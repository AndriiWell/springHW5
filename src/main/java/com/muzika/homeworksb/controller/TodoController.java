package com.muzika.homeworksb.controller;

import com.muzika.homeworksb.dto.TaskHistoryResponseDto;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.dto.TodoUpdateDto;
import com.muzika.homeworksb.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoService service;

    @PostMapping
    public TodoResponseDto create(Authentication authentication, @RequestBody @Valid TodoCreateDto createDto) {
        String userName = authentication.getName();
        return service.save(userName, createDto);
    }

    @PutMapping("/{id}")
    public TodoResponseDto update(Authentication authentication, @PathVariable Long id, @RequestBody @Valid TodoUpdateDto updateDto) {
        String userName = authentication.getName();
        return service.update(userName, id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}/history")
    public List<TaskHistoryResponseDto> history(Authentication authentication, @PathVariable Long id) {
        String userName = authentication.getName();
        return service.findHistoryById(userName, id);
    }
}
