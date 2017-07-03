package com.agilesolutions.todolist.controller;

import com.agilesolutions.todolist.domain.DuplicateTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.services.tasks.AddTask;
import com.agilesolutions.todolist.services.tasks.ListTasks;
import com.agilesolutions.todolist.services.tasks.MarkTaskAsAvailable;
import com.agilesolutions.todolist.services.tasks.MarkTaskAsFinished;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/{todolist-id}/tasks")
public class TasksController {

    private final AddTask addTask;
    private final ListTasks listTasks;
    private final MarkTaskAsAvailable markTaskAsAvailable;
    private final MarkTaskAsFinished markTaskAsFinished;

    @Inject
    public TasksController(
            AddTask addTask,
            ListTasks listTasks,
            MarkTaskAsAvailable markTaskAsAvailable,
            MarkTaskAsFinished markTaskAsFinished) {
        this.addTask = addTask;
        this.listTasks = listTasks;
        this.markTaskAsAvailable = markTaskAsAvailable;
        this.markTaskAsFinished = markTaskAsFinished;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addTask(
            @PathVariable("todolist-id") TodoListId todoListId,
            @RequestParam(name = "description") String description) {
        try {
            return ResponseEntity.ok(addTask.execute(todoListId, description));
        } catch (NonexistentTodoListException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
        } catch (DuplicateTaskItemException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public void listTasks() {

    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/available")
    public void markTaskAsAvailable() {

    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/finished")
    public void markTaskAsFinished() {

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void removeTask() {

    }
}
