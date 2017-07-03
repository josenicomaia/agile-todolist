package com.agilesolutions.todolist.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class TaskItem {
    @Getter
    private TaskItemId id;
    @Getter
    private String description;
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
