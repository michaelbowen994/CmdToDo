package com.michaelbowen.cmdtodo.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class TodoJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(TodoJsonDataLoader.class);
    private final JdbcTodoRepository todoRepository;
    private final ObjectMapper objectMapper;

    public TodoJsonDataLoader(JdbcTodoRepository todoRepository, ObjectMapper objectMapper)
    {
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(todoRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/todos.json")) {
                Todos allTodos = objectMapper.readValue(inputStream, Todos.class);
                log.info("Reading {} todos from JSON data and saving to a database.", allTodos.todos().size());
                todoRepository.saveAll(allTodos.todos());
            } catch ( IOException e ){
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Todos from JSON data because the collection contains data.");
        }
    }
}
