package com.agilesolutions.todolist.domain;

import lombok.Getter;

public class TaskItem {
    @Getter
    private TaskItemId id;
    @Getter
    private String description;
    private Boolean finished;
    
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
