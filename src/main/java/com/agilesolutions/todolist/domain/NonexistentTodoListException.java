package com.agilesolutions.todolist.domain;

public class NonexistentTodoListException extends Exception {

    public NonexistentTodoListException() {
    }

    public NonexistentTodoListException(String message) {
        super(message);
    }

    public NonexistentTodoListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentTodoListException(Throwable cause) {
        super(cause);
    }

}
