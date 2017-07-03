package com.agilesolutions.todolist.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TodoListId {
    @Getter
    private UUID value;

    public TodoListId(String candidate) {
        this.value = UUID.fromString(candidate);
    }
}
