package com.agilesolutions.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String description;

    public String getDescription() {
        return description;
    }
}
