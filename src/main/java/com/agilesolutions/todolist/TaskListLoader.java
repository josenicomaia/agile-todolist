package com.agilesolutions.todolist;

import com.agilesolutions.todolist.domain.DuplicateTaskItemException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TaskItemId;
import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import java.util.HashSet;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TaskListLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Inject
    private EntityManager em;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            TodoList todoList = new TodoList(new TodoListId(UUID.fromString("1fa96fb1-d622-4208-8227-057a82121075")), new HashSet<>());
            todoList.addTask(new TaskItem(new TaskItemId(UUID.fromString("940bcc02-2798-47b9-a57a-4207cafb940a")), "one"));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "two"));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "three"));

            em.persist(todoList);
            em.flush();
        } catch (DuplicateTaskItemException ex) {

        }
    }

}
