package com.michaelbowen.cmdtodo.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.Assert;

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
        return jdbcClient.sql("SELECT * FROM todo")
                .query(Todo.class)
                .list();
//        return jdbcClient.sql("SELECT * FROM todo")
//            .query(Todo.class)
//            .list();
    }
    
    public Optional<Todo> findById(Integer id)
    {
        log.info("Request to get todo with id: {}", id);
        return jdbcClient.sql("SELECT id, title, created_on, last_updated, due_date, description, completed, updates FROM Todo WHERE id = :id")
            .param("id", id)
            .query(Todo.class)
            .optional();
    }

    public void create(Todo todo)
    {
        var update = jdbcClient.sql("INSERT INTO todo (id, title, created_on, last_updated, due_date, description, completed, updates) VALUES (?,?,?,?,?,?,?,?)")
                .params(List.of(todo.id(), todo.title(), todo.createdOn(), todo.lastUpdated(), todo.dueDate(), todo.description(), todo.completed(), todo.updates()))
                .update();
        Assert.state( update == 1, "Failed to create todo" + todo.title());
    }

    public void update(Todo todo, Integer id) {
        var update = jdbcClient.sql("update todo set title = ?, created_on = ?, last_updated = ?, due_date = ?, description = ?, completed = ?, updates = ? where id = ?")
                .params(List.of(todo.title(), todo.createdOn(), todo.lastUpdated(), todo.dueDate(), todo.description(), todo.completed(), todo.updates(), id))
                .update();
        Assert.state( update == 1, "Failed to update todo " + todo.title());
    }

    public void delete(Integer id) {
        var update =jdbcClient.sql("delete from todo where id = :id")
                .param(new String( "id"), id)
                .update();
        Assert.state(update == 1, "Failed to delete todo " + id);
    }

    public int count() {
         return jdbcClient.sql("select * from todo").query().listOfRows().size();
    }

    public void saveAll(List<Todo> todos) {
        todos.stream().forEach(this::create);
    }

    public List<Todo> findByTitle(String title) {
        return jdbcClient.sql("select * from todo where title = :title")
                .param(new String("title"), title)
                .query(Todo.class)
                .list();
    }
}
