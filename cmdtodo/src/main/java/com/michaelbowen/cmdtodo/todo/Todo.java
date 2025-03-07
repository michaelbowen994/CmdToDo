package com.michaelbowen.cmdtodo.todo;

import java.time.LocalDateTime;
import java.util.List;

public record Todo(int id,
                   String title,
                   LocalDateTime createdOn,
                   LocalDateTime lastUpdated,
                   LocalDateTime dueDate,
                   boolean completed,
                   String description,
                   String updates){

}