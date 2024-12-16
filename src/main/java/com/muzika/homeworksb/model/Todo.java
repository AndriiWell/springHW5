package com.muzika.homeworksb.model;

import com.muzika.homeworksb.enums.PriorityEnum;
import com.muzika.homeworksb.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a task that users will manage
 */
@Data
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Short description or title of the task
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of the task
     */
    private String description;

    /**
     * When the task is due
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /**
     * Priority of the task.
     * e.g., LOW, MEDIUM, HIGH. Store enum value as varchar in DB
     */
    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    /**
     * Status of the task
     * e.g., PENDING, IN_PROGRESS, COMPLETED
     */
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    /**
     * When the task was created
     */
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * When the task was last updated
     */
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * The ID of the user who owns the task
     * Hardcode value 1 for now. We will replace it when Security is implemented.
     */
    @Column(name = "user_id")
    private Long userId = 1L;
}
