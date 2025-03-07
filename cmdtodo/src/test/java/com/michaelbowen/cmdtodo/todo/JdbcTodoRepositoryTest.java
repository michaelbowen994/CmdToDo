package com.michaelbowen.cmdtodo.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcTodoRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcTodoRepositoryTest {

    @Autowired
    JdbcTodoRepository repository;

//            "id": 1,
//                    "title": "Read my Bible",
//                    "createdOn": "2021-07-01T06:05:00.000000",
//                    "lastUpdated": "2021-07-01T06:05:00.000000",
//                    "dueDate": "2025-01-30T06:05:00.000000",
//                    "description": "Read the Bible from Genesis to Revelation",
//                    "done": false,
//                    "updates": "Read Genesis 1-3"
    @BeforeEach
    void setUp() {
        repository.create(
                new Todo(
                        1,
                        "Test Todo 1",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        LocalDateTime.now().plusDays(2),
                        false,
                        "Unit test for todo database.",
                        "Run tests to make sure this works."
                )
        );
        repository.create(
                new Todo (
                        2,
                        "Test Todo 2",
                        LocalDateTime.now().minusDays(5),
                        LocalDateTime.now().minusDays(3),
                        LocalDateTime.now(),
                        false,
                        "Unit test todo for database integration",
                        "completed initial setup"
                )
        );
    }

    @Test
    void findAllTodosTest() {
        List<Todo> todos = repository.findAll();
        assertEquals(2, todos.size());
    }

    @Test
    void findTodoByIdTest() {
        var foundTodo = repository.findById(1).get();
        assertEquals("Test Todo 1", foundTodo.title());
        assertFalse(foundTodo.completed());
    }

    @Test
    void shouldNotFindTodoWithInvalidId() {
        var notFoundTodo = repository.findById(3);
        assertTrue(notFoundTodo.isEmpty());
    }

    @Test
    void shouldCreateNewTodo() {
        repository.create(
            new Todo (
                3,
                "Test Todo 3",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusDays(1),
                false,
                "Adding new todo to database.",
                "Just trying to test all functionality."
            )
        );

        List<Todo> todos = repository.findAll();
        assertEquals(3, todos.size());

        repository.delete(3);
    }

    @Test
    void shouldDeleteTodo() {
        var todo = repository.findById(1).get();
        assertEquals("Test Todo 1", todo.title());

        repository.delete(1);
        List<Todo> todos = repository.findAll();
        assertEquals(1, todos.size());

        repository.create(todo);
        todos = repository.findAll();
        assertEquals(2, todos.size());
    }

    @Test
    void shouldUpdateTodo() {
        var todo = repository.findById(2).get();
        Todo newTodo = new Todo (
                todo.id(),
                "Test Todo 2",
                todo.createdOn(),
                todo.lastUpdated(),
                todo.dueDate(),
                true,
                todo.description(),
                todo.updates()
        );

        repository.update(newTodo, 2);

        var updatedTodo = repository.findById(2).get();
        assertEquals(2, updatedTodo.id());
        assertTrue(updatedTodo.completed());
        assertEquals("Test Todo 2", updatedTodo.title());

        newTodo = new Todo (
            todo.id(),
            todo.title(),
            todo.createdOn(),
            todo.lastUpdated(),
            todo.dueDate(),
            todo.completed(),
            todo.description(),
            todo.updates()
        );

        repository.update(newTodo, 2);
    }


}
