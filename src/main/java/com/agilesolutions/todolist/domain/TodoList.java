package com.agilesolutions.todolist.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todolists")
public class TodoList {

    @Getter
    @EmbeddedId
    private TodoListId id;

    @Getter
    @OneToMany
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
