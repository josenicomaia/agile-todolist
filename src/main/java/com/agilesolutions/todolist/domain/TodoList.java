package com.agilesolutions.todolist.domain;

import java.util.List;
import lombok.Getter;

public class TodoList {
    @Getter
    private TodoListId id;
    @Getter
    private List<TaskItem> tasks;
    
    public void addTask(TaskItem task) {
        tasks.add(task);
    }
    
    public void markTaskAsFinished(TaskItem task) {
        task.markAsFinished();
    }
    
    public void markTaskAsAvailable(TaskItem task) {
        task.markAsAvailable();
    }
    
    public void removeTask(TaskItem task) {
        tasks.remove(task);
    }
}
