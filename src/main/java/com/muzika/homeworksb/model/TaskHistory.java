package com.muzika.homeworksb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Tracks changes made to Todo tasks.
 */
@Data
@Entity
@Table(name = "task_history")
public class TaskHistory {

    /**
     * Unique identifier for the TaskHistory entry
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to related Todo item
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_id", nullable = false)
    @JsonIgnore
    private Todo todo;

    /**
     * to simplify the task you can use Todo toString() method response and store here. For advanced solution - convert Todo entity to JSON format and store it
     */
    @Column(name = "old_state")
    private String oldState;

    /**
     * String (same as for oldState)
     */
    @Column(name = "new_state")
    private String newState;

    /**
     * The timestamp when the change was made
     */
    @Column(name = "changed_date")
    private LocalDateTime changeDate;

    /**
     * The name or ID of the user who made the change). Feel free to leave it null for now.
     */
    @Column(name = "changed_by")
    private String changedBy;
}
