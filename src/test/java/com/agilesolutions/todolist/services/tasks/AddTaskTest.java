package com.agilesolutions.todolist.services.tasks;

import com.agilesolutions.todolist.domain.DuplicateTaskItemException;
import com.agilesolutions.todolist.domain.NonexistentTodoListException;
import com.agilesolutions.todolist.domain.TaskItem;
import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddTaskTest {
    @Inject
    private AddTask addTask;
    
    @MockBean
    private TodoListRepository repository;
    
    @Test
    public void given_existing_todolist_and_valid_description_then_add_task() throws DuplicateTaskItemException, NonexistentTodoListException, NonexistentTodoListException {
        TodoList foundTodoList = new TodoList();
        
        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));
        
        TaskItem taskItem = addTask.execute(new TodoListId("1fa96fb1-d622-4208-8227-057a82121075"), "");
        
        assertNotNull(taskItem.getId());
        assertEquals(taskItem.getDescription(), "");
        assertFalse(taskItem.isFinished());
        
        verify(repository).find(any());
        verify(repository).save(foundTodoList);
    }
    
    @Test(expected = NonexistentTodoListException.class)
    public void given_nonexisting_todolist_and_valid_description_then_fail_task_creation() throws DuplicateTaskItemException, NonexistentTodoListException {
        given(repository.find(any()))
                .willReturn(Optional.empty());
        
        addTask.execute(new TodoListId("1fa96fb1-d622-4208-8227-057a82121075"), "");
    }
    
    @Test(expected = DuplicateTaskItemException.class)
    public void given_existing_todolist_and_valid_description_then_fail_create_new_task_after_retries() throws DuplicateTaskItemException, NonexistentTodoListException, NonexistentTodoListException {
        TodoList foundTodoList = spy(new TodoList());
        
        doThrow(DuplicateTaskItemException.class).when(foundTodoList).addTask(any());
        
        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));
        
        try {
            addTask.execute(new TodoListId("1fa96fb1-d622-4208-8227-057a82121075"), "");
        } catch(DuplicateTaskItemException ex) {
            verify(foundTodoList, times(3)).addTask(any());
            
            throw ex;
        }
    }
    
    @Test
    public void given_existing_todolist_and_valid_description_then_add_task_after_one_retry() throws DuplicateTaskItemException, NonexistentTodoListException, NonexistentTodoListException {
        TodoList foundTodoList = spy(new TodoList());
        
        doThrow(DuplicateTaskItemException.class).doCallRealMethod().when(foundTodoList).addTask(any());
        
        given(repository.find(any()))
                .willReturn(Optional.of(foundTodoList));
        
        TaskItem taskItem = addTask.execute(new TodoListId("1fa96fb1-d622-4208-8227-057a82121075"), "");
        
        assertNotNull(taskItem.getId());
        assertEquals(taskItem.getDescription(), "");
        assertFalse(taskItem.isFinished());
        
        verify(repository, times(2)).find(any());
        verify(repository).save(foundTodoList);
        verify(foundTodoList, times(2)).addTask(any());
    }
}
