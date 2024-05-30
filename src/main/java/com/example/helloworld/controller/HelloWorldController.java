package com.example.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.helloworld.model.Student;
import com.example.helloworld.service.StudentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class HelloWorldController {

    private final StudentService service;

    @Autowired
    public HelloWorldController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String sendGreetings() {
        return "Hello, World!";
    }

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(service.addNewStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> retriveAllStudents(@PathVariable Long id) {
        return new ResponseEntity<>(service.findStudent(id), HttpStatus.OK);
    }

    @PutMapping("/student")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        return new ResponseEntity<>(service.updateStudent(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteStudent(id), HttpStatus.OK);
    }
}
