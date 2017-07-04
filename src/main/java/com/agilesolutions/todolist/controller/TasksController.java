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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> addTask(
            @PathVariable("todolist-id") TodoListId todoListId,
            @RequestParam(name = "description") String description) {
        logger.info("Adding a new task to " + todoListId);

        try {
            return ok(new AddTaskResponse(addTask.execute(todoListId, description)).toString());
        } catch (NonexistentTodoListException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new AddTaskResponse(ex).toString());
        } catch (DuplicateTaskItemException | RuntimeException ex) {
            logger.error("An error occurred while adding new task to " + todoListId + ". Message: " + ex.getMessage());
            ex.printStackTrace();
            
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AddTaskResponse(ex).toString());
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> listTasks(
            @PathVariable("todolist-id") TodoListId todoListId) {
        logger.info("Listing tasks from " + todoListId);

        try {
            return ok(new ListTaskResponse(listTasks.execute(todoListId)).toString());
        } catch (NonexistentTodoListException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new ListTaskResponse(ex).toString());
        } catch (RuntimeException ex) {
            logger.error("An error occurred while listing tasks from " + todoListId + ". Message: " + ex.getMessage());
            ex.printStackTrace();

            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListTaskResponse(ex).toString());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/available", produces = "application/json")
    public ResponseEntity<String> markTaskAsAvailable(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        logger.info("Marking as available task " + taskItemId + " from " + todoListId);

        try {
            return ok(new MarkAsAvailableTaskResponse(markAsAvailableTask.execute(todoListId, taskItemId)).toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new MarkAsAvailableTaskResponse(ex).toString());
        } catch (RuntimeException ex) {
            logger.error("An error occurred while marking as available task " + taskItemId + " from " + todoListId + ". Message: " + ex.getMessage());
            ex.printStackTrace();

            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MarkAsAvailableTaskResponse(ex).toString());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/finished", produces = "application/json")
    public ResponseEntity<String> markTaskAsFinished(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        logger.info("Marking as finished task " + taskItemId + " from " + todoListId);

        try {
            return ok(new MaskAsFinishedTaskResponse(markAsFinishedTask.execute(todoListId, taskItemId)).toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new MaskAsFinishedTaskResponse(ex).toString());
        } catch (RuntimeException ex) {
            logger.error("An error occurred while marking as finished task " + taskItemId + " from " + todoListId + ". Message: " + ex.getMessage());
            ex.printStackTrace();

            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaskAsFinishedTaskResponse(ex).toString());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}", produces = "application/json")
    public ResponseEntity<String> removeTask(
            @PathVariable("todolist-id") TodoListId todoListId,
            @PathVariable("id") TaskItemId taskItemId) {
        logger.info("Removing task " + taskItemId + " from " + todoListId);

        try {
            removeTask.execute(todoListId, taskItemId);

            return ok(new RemoveTaskResponse().toString());
        } catch (NonexistentTodoListException | NonexistentTaskItemException ex) {
            return status(HttpStatus.NOT_FOUND)
                    .body(new RemoveTaskResponse(ex).toString());
        } catch (RuntimeException ex) {
            logger.error("An error occurred while removing task " + taskItemId + " from " + todoListId + ". Message: " + ex.getMessage());
            ex.printStackTrace();
            
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RemoveTaskResponse(ex).toString());
        }
    }
}
