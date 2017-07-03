package com.agilesolutions.todolist.domain;

import java.util.Optional;

public interface TodoListRepository {
    public void save(TodoList todoList);

    public Optional<TodoList> find(TodoListId todoListId);
}
