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
public class TaskItemId implements Serializable {

    @Getter
    private UUID value;

    public TaskItemId(String candidate) {
        this.value = UUID.fromString(candidate);
    }
}
