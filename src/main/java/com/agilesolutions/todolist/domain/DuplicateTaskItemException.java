package com.agilesolutions.todolist.domain;

public class DuplicateTaskItemException extends Exception {

    public DuplicateTaskItemException() {
    }

    public DuplicateTaskItemException(String message) {
        super(message);
    }

    public DuplicateTaskItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTaskItemException(Throwable cause) {
        super(cause);
    }
}
