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
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Stage 1 - Logic and Personality Test", true));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Stage 2 - Technical interview", true));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Checklist Exercise"));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Profitable Packages Report"));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Responsive Interface with Boostrap"));
            todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), "Stage 3 - Final boss"));

            em.persist(todoList);
            em.flush();
        } catch (DuplicateTaskItemException ex) {

        }
    }

}
