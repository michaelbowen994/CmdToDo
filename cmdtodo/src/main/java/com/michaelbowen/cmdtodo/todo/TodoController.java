package com.michaelbowen.cmdtodo.todo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final JdbcTodoRepository repository;

    public TodoController(JdbcTodoRepository repository){
        this.repository = repository;
    }

   @GetMapping("")
   List<Todo> findAll() {
        return repository.findAll();
   } 
}
