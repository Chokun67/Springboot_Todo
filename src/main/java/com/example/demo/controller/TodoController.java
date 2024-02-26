package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.repo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/getAllTodos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        try {
            List<Todo> todoList = new ArrayList<>();
            todoRepository.findAll().forEach(todoList::add);

            if (todoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTodoById/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todoObj = todoRepository.findById(id);
        if (todoObj.isPresent()) {
            return new ResponseEntity<>(todoObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addTodo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        try {
            Todo todoObj = todoRepository.save(todo);
            System.out.println("Todo Object: " + todoObj); // แสดง Object ที่บันทึกลงฐานข้อมูล
            return new ResponseEntity<>(todoObj, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // แสดงข้อความผิดพลาด
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateTodo/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            Optional<Todo> todoData = todoRepository.findById(id);
            if (todoData.isPresent()) {
                Todo updatedTodoData = todoData.get();
                updatedTodoData.setTitle(todo.getTitle());
                updatedTodoData.setAuthor(todo.getAuthor());

                Todo todoObj = todoRepository.save(updatedTodoData);
                return new ResponseEntity<>(todoObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTodoById/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteAllTodos")
    public ResponseEntity<HttpStatus> deleteAllTodos() {
        try {
            todoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
