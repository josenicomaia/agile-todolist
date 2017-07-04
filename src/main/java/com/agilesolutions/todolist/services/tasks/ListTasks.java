package com.agilesolutions.todolist.services.tasks;

import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ListTasks {

    private TodoListRepository repository;

    @Inject
    public ListTasks(TodoListRepository repository) {
        this.repository = repository;
    }
    
    public Set<TaskItem> execute(TodoListId todoListId) throws NonexistentTodoListException {
        return this.repository.find(todoListId)
                .orElseThrow(() -> new NonexistentTodoListException("Unable to find the Todo List."))
                .getTasks();
    }
    
}
