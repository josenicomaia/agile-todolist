package com.agilesolutions.todolist.controller;

import com.agilesolutions.todolist.domain.DuplicateTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItemId;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.services.tasks.AddTask;
import com.agilesolutions.todolist.services.tasks.ListTasks;
import com.agilesolutions.todolist.services.tasks.MarkAsAvailableTask;
import com.agilesolutions.todolist.services.tasks.MarkAsFinishedTask;
import com.agilesolutions.todolist.services.tasks.RemoveTask;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;
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
    private final MarkAsAvailableTask markAsAvailableTask;
    private final MarkAsFinishedTask markAsFinishedTask;
    private final RemoveTask removeTask;

    @Inject
    public TasksController(
            AddTask addTask,
            ListTasks listTasks,
            MarkAsAvailableTask markAsAvailableTask,
            MarkAsFinishedTask markAsFinishedTask,
            RemoveTask removeTask) {
        this.addTask = addTask;
        this.listTasks = listTasks;
        this.markAsAvailableTask = markAsAvailableTask;
        this.markAsFinishedTask = markAsFinishedTask;
        this.removeTask = removeTask;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addTask(
            @PathVariable("todolist-id") TodoListId todoListId,
            @RequestParam(name = "description") String description) {
        try {
            return ok(new AddTaskResponse(addTask.execute(todoListId, description)).toString());
        } catch (NonexistentTodoListException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new AddTaskResponse(ex.getMessage()).toString());
        } catch (DuplicateTaskItemException | RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AddTaskResponse(ex.getMessage()).toString());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> listTasks(
            @PathVariable("todolist-id") TodoListId todoListId) {
        try {
            return ok(new ListTaskResponse(listTasks.execute(todoListId)).toString());
        } catch (NonexistentTodoListException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new ListTaskResponse(ex.getMessage()).toString());
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListTaskResponse(ex.getMessage()).toString());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/available")
    public ResponseEntity<String> markTaskAsAvailable(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        try {
            return ok(new MarkAsAvailableTaskResponse(markAsAvailableTask.execute(todoListId, taskItemId)).toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new MarkAsAvailableTaskResponse(ex.getMessage()).toString());
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MarkAsAvailableTaskResponse(ex.getMessage()).toString());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/finished")
    public ResponseEntity<String> markTaskAsFinished(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        try {
            return ok(new MaskAsFinishedTaskResponse(markAsFinishedTask.execute(todoListId, taskItemId)).toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new MaskAsFinishedTaskResponse(ex.getMessage()).toString());
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaskAsFinishedTaskResponse(ex.getMessage()).toString());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> removeTask(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        try {
            removeTask.execute(todoListId, taskItemId);
            
            return ok(new RemoveTaskResponse().toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new RemoveTaskResponse(ex.getMessage()).toString());
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RemoveTaskResponse(ex.getMessage()).toString());
        }
    }
}
