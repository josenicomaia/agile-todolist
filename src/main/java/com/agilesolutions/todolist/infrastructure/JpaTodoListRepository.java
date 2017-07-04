package com.agilesolutions.todolist.infrastructure;

import com.agilesolutions.todolist.domain.TodoList;
import com.agilesolutions.todolist.domain.TodoListId;
import com.agilesolutions.todolist.domain.TodoListRepository;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        TypedQuery<TodoList> query = em.createQuery(
                "SELECT tl "
                        + "FROM TodoList tl "
                        + "LEFT JOIN tl.tasks t "
                        + "WHERE tl.id = :id", TodoList.class)
                .setParameter("id", todoListId);
        
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
