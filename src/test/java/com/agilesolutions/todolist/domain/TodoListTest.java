package com.agilesolutions.todolist.domain;

import java.util.HashSet;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TodoListTest {

    @Test
    public void given_empty_todolist_then_add_task() throws DuplicateTaskItemException {
        TodoList todoList = new TodoList();
        todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), ""));

        assertTrue(todoList.getTasks().size() == 1);
    }

    @Test
    public void given_todolist_with_one_item_then_add_task() throws DuplicateTaskItemException {
        HashSet<TaskItem> tasks = new HashSet<>();
        tasks.add(new TaskItem(new TaskItemId(UUID.randomUUID()), ""));

        TodoList todoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);
        todoList.addTask(new TaskItem(new TaskItemId(UUID.randomUUID()), ""));

        assertTrue(todoList.getTasks().size() == 2);
    }

    @Test(expected = DuplicateTaskItemException.class)
    public void given_todolist_with_one_item_then_fail_adding_duplicated_task() throws DuplicateTaskItemException {
        HashSet<TaskItem> tasks = new HashSet<>();
        tasks.add(new TaskItem(new TaskItemId(UUID.randomUUID()), ""));

        TodoList todoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);
        todoList.addTask(todoList.getTasks().stream().findFirst().get());
    }

    @Test
    public void given_todolist_then_check_id() {
        TodoListId todoListId = new TodoListId(UUID.randomUUID());
        TodoList todoList = new TodoList(todoListId, null);

        assertEquals(todoList.getId(), todoListId);
    }
    
    @Test
    public void given_todolist_with_one_item_then_verify_same_tasks() {
        HashSet<TaskItem> tasks = new HashSet<>();
        tasks.add(new TaskItem(new TaskItemId(UUID.randomUUID()), ""));
        TodoList todoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);
        assertEquals(todoList.getTasks(), tasks);
    }
    
    @Test
    public void given_todolist_with_two_item_then_verify_same_tasks() {
        HashSet<TaskItem> tasks = new HashSet<>();
        tasks.add(new TaskItem(new TaskItemId(UUID.randomUUID()), "one"));
        tasks.add(new TaskItem(new TaskItemId(UUID.randomUUID()), "two"));
        TodoList todoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);
        assertEquals(todoList.getTasks(), tasks);
    }
}
