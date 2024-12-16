package com.muzika.homeworksb.dto;

import com.muzika.homeworksb.enums.PriorityEnum;
import com.muzika.homeworksb.enums.StatusEnum;
import com.muzika.homeworksb.validation.Priority;
import com.muzika.homeworksb.validation.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TodoUpdateDto(
    @NotEmpty(message = "Title can't be empty")
    @Length(max = 100, message = "Title must be <= 100 characters")
    String title,

    @Length(max = 500, message = "Description must be <= 500 characters")
    String description,

    @NotNull(message = "Due date is required")
    LocalDateTime dueDate,

    @Priority PriorityEnum priority,

    @NotNull(message = "Status is required")
    @Status StatusEnum status
) { }
