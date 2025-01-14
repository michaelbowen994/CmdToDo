package com.michaelbowen.cmdtodo.todo;

import java.time.LocalDateTime;
import java.util.List;

public record Todo(int id,
                   String title,
                   LocalDateTime created,
                   LocalDateTime lastUpdated,
                   LocalDateTime due,
                   boolean completed,
                   List<String> tags,
                   String description,
                   List<String> updates){

}