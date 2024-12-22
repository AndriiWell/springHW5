package com.muzika.homeworksb.dto;

import com.muzika.homeworksb.enums.PriorityEnum;
import com.muzika.homeworksb.enums.StatusEnum;

import java.time.LocalDateTime;

public record TodoResponseDto(
    Long id,
    String title,
    String description,
    LocalDateTime dueDate,
    PriorityEnum priority,
    StatusEnum status,
    LocalDateTime createdDate,
    LocalDateTime updatedDate,
    Long userId
) { }
