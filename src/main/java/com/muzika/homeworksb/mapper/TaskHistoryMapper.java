package com.muzika.homeworksb.mapper;

import com.muzika.homeworksb.config.MapperConfig;
import com.muzika.homeworksb.dto.TaskHistoryResponseDto;
import com.muzika.homeworksb.model.TaskHistory;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TaskHistoryMapper {
    TaskHistoryResponseDto toDto(TaskHistory task);
}
