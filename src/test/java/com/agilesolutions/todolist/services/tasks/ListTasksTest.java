package com.agilesolutions.todolist.services.tasks;

import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TaskItemId;
import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListTasksTest {

    @Inject
    private ListTasks listTasks;

    @MockBean
    private TodoListRepository repository;

    @Test
    public void given_existing_empty_todolist_then_retrieve_the_tasks() throws NonexistentTodoListException {
        TodoList foundTodoList = new TodoList();

        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));

        Set<TaskItem> foundTasks = listTasks.execute(new TodoListId(UUID.randomUUID()));
        assertNotNull(foundTasks);
        assertTrue(foundTasks.isEmpty());

        verify(repository).find(any());
    }

    @Test
    public void given_existing_todolist_with_one_item_then_retrieve_the_tasks() throws NonexistentTodoListException {
        Set<TaskItem> tasks = new HashSet<>();
        TaskItem ti = new TaskItem(new TaskItemId(UUID.randomUUID()), "one");
        tasks.add(ti);

        TodoList foundTodoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);

        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));

        Set<TaskItem> foundTasks = listTasks.execute(foundTodoList.getId());
        assertTrue(foundTasks.size() == 1);
        assertTrue(foundTasks.contains(ti));

        verify(repository).find(foundTodoList.getId());
    }

    @Test
    public void given_existing_todolist_with_two_item_then_retrieve_the_tasks() throws NonexistentTodoListException {
        Set<TaskItem> tasks = new HashSet<>();
        TaskItem ti1 = new TaskItem(new TaskItemId(UUID.randomUUID()), "one");
        TaskItem ti2 = new TaskItem(new TaskItemId(UUID.randomUUID()), "two");
        tasks.add(ti1);
        tasks.add(ti2);

        TodoList foundTodoList = new TodoList(new TodoListId(UUID.randomUUID()), tasks);

        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));

        Set<TaskItem> foundTasks = listTasks.execute(foundTodoList.getId());
        assertTrue(foundTasks.size() == 2);
        assertTrue(foundTasks.contains(ti1));
        assertTrue(foundTasks.contains(ti2));

        verify(repository).find(foundTodoList.getId());
    }

    @Test(expected = NonexistentTodoListException.class)
    public void given_nonexisting_todolist_then_fail_retrieving_tasks() throws NonexistentTodoListException {
        given(repository.find(any()))
                .willReturn(Optional.empty());

        try {
            listTasks.execute(new TodoListId(UUID.randomUUID()));
        } catch (NonexistentTodoListException ex) {
            verify(repository).find(any());
            
            throw ex;
        }
    }
}
