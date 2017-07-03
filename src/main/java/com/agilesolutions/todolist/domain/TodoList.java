package com.agilesolutions.todolist.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    @Getter
    private TodoListId id;
    @Getter
    private Set<TaskItem> tasks = new HashSet<>();

    public void addTask(TaskItem newTaskItem) throws DuplicateTaskItemException {
        if (tasks.stream().anyMatch((taskItem) -> taskItem.getId().equals(newTaskItem.getId()))) {
            throw new DuplicateTaskItemException("Duplicate task id found.");
        }
        
        tasks.add(newTaskItem);
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
