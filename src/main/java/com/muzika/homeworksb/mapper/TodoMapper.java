package com.muzika.homeworksb.mapper;

import com.muzika.homeworksb.config.MapperConfig;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
    TodoResponseDto toDto(Todo todo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Todo toModel(TodoCreateDto todoCreateDto);
}
