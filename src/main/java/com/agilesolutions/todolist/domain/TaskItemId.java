package com.agilesolutions.todolist.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TaskItemId {
    @Getter
    private UUID value;
    
    public TaskItemId(String candidate) {
        this.value = UUID.fromString(candidate);
    }
}
