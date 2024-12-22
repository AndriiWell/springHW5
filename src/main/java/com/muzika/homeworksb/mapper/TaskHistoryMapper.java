package com.muzika.homeworksb.mapper;

import com.muzika.homeworksb.config.MapperConfig;
import com.muzika.homeworksb.dto.TaskHistoryResponseDto;
import com.muzika.homeworksb.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TaskHistoryMapper {
    @Mapping(source = "todo.id", target = "todoId")
    TaskHistoryResponseDto toDto(TaskHistory task);
}
