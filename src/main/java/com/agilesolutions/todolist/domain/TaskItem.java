package com.agilesolutions.todolist.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tasks")
public class TaskItem {

    @Getter
    @EmbeddedId
    private TaskItemId id;

    @Getter
    @NotNull
    private String description;

    @NotNull
    private Boolean finished;

    public TaskItem(TaskItemId id, String description) {
        this.id = id;
        this.description = description;
        finished = false;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void markAsFinished() {
        finished = true;
    }

    public void markAsAvailable() {
        finished = false;
    }
}
