package com.agilesolutions.todolist.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class TodoListId implements Serializable {

    @Getter
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID value;

    public TodoListId(String candidate) {
        this.value = UUID.fromString(candidate);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
