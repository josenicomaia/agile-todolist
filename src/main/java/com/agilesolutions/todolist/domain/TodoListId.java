package com.agilesolutions.todolist.domain;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TodoListId implements Serializable {
    @Getter
    private UUID value;

    public TodoListId(String candidate) {
        this.value = UUID.fromString(candidate);
    }
}
