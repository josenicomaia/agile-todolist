package com.agilesolutions.todolist.services.tasks;

import com.agilesolutions.todolist.domain.NonexistentTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TaskItemId;
import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MarkAsFinishedTask {

    private final TodoListRepository repository;

    @Inject
    public MarkAsFinishedTask(TodoListRepository repository) {
        this.repository = repository;
    }

    public TaskItem execute(TodoListId todoListId, TaskItemId taskItemId) throws NonexistentTodoListException, NonexistentTaskItemException {
        TodoList todoList = repository.find(todoListId)
                .orElseThrow(() -> new NonexistentTodoListException());

        TaskItem taskItem = todoList.getTasks()
                .stream()
                .filter((t) -> t.getId().equals(taskItemId))
                .findFirst()
                .orElseThrow(() -> new NonexistentTaskItemException());

        todoList.markTaskAsFinished(taskItem);
        repository.save(todoList);

        return taskItem;
    }

}
