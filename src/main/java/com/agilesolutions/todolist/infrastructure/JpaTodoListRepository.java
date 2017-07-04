package com.agilesolutions.todolist.infrastructure;

import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTodoListRepository implements TodoListRepository {

    private final EntityManager em;

    @Inject
    public JpaTodoListRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional
    @Override
    public void save(TodoList todoList) {
        em.persist(todoList);
        em.flush();
    }

    @Transactional
    @Override
    public Optional<TodoList> find(TodoListId todoListId) {
        TypedQuery<TodoList> query = em.createQuery("select tl form TodoList tl where lt.id = :id", TodoList.class)
                .setParameter("id", todoListId.getValue().toString());
        
        return Optional.ofNullable(query.getSingleResult());
    }

}
