package com.michaelbowen.cmdtodo.todo;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

   @GetMapping("/{id}")
    Todo findById(@PathVariable Integer id) {
        Optional<Todo> todo = repository.findById(id);

        if(todo.isEmpty())

        {
            throw new TodoNotFoundException();
        }

        return todo.get();
   }

   @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Todo todo) {
        repository.create(todo);
   }
}
