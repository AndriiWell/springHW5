package com.muzika.homeworksb.mapper;

import com.muzika.homeworksb.config.MapperConfig;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.model.Todo;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
    TodoResponseDto toDto(Todo todo);

    Todo toModel(TodoCreateDto todoCreateDto);
}
