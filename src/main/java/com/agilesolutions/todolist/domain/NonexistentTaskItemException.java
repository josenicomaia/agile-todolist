package com.agilesolutions.todolist.domain;

public class NonexistentTaskItemException extends Exception {

    public NonexistentTaskItemException() {
    }

    public NonexistentTaskItemException(String message) {
        super(message);
    }

    public NonexistentTaskItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentTaskItemException(Throwable cause) {
        super(cause);
    }
    
}
