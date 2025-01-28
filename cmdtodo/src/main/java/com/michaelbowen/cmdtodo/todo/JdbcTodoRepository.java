package com.michaelbowen.cmdtodo.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;

@Repository
public class JdbcTodoRepository {

    private List<Todo> todos = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(JdbcTodoRepository.class);
    private final JdbcClient jdbcClient;  

    public JdbcTodoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Todo> findAll() {
        log.info("Request to get all todos");
        return jdbcClient.sql("SELECT * FROM todos")
            .query(Todo.class)
            .list();
    }    
    
    public Optional<Todo> findById(Integer id)
    {
        log.info("Request to get todo with id: {}", id);
        return jdbcClient.sql("SELECT id, title, created_on, last_updated, due_date, description, done, updates FROM Todo WHERE id = :id")
            .param("id", id)
            .query(Todo.class)
            .optional();
    }
}
