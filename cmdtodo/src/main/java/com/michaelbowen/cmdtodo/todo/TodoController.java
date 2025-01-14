package com.michaelbowen.cmdtodo.todo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos/")
public class TodoController {

    private final JdbcTodoRepository repository;
}
