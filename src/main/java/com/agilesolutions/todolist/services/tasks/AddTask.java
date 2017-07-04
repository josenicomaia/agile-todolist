package com.agilesolutions.todolist.services.tasks;

import com.agilesolutions.todolist.domain.DuplicateTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TaskItemId;
import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Named
public class AddTask {

    private final TodoListRepository repository;

    @Inject
    public AddTask(TodoListRepository repository) {
        this.repository = repository;
    }

    // IdempotentRetry
    @Retryable(maxAttempts = 3, include = DuplicateTaskItemException.class, backoff = @Backoff(0))
    public TaskItem execute(TodoListId todoListId, String description) throws DuplicateTaskItemException, NonexistentTodoListException {
        TodoList todoList = repository.find(todoListId)
                .orElseThrow(() -> new NonexistentTodoListException("Unable to find the Todo List."));

        TaskItem newTaskItem = new TaskItem(new TaskItemId(UUID.randomUUID()), description);
        todoList.addTask(newTaskItem);
        repository.save(todoList);

        return newTaskItem;
    }

}
