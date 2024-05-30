package com.example.helloworld.service;

import com.example.helloworld.model.Student;
import com.example.helloworld.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // inserting new record
    public Student addNewStudent(Student student) {
        return repository.save(student);
    }

    // find existing record
    @Cacheable(cacheNames = {"studentCache"}, key = "#studentId")
    public Student findStudent(Long studentId) {
        // simulating backend querying delay
        simulateBackendCall();
        return repository.findById(studentId).get();
    }

    // updating existing record
    @CachePut(cacheNames = {"studentCache"}, key="#student.studentId")
    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    // deleting existing record
    @CacheEvict(cacheNames = {"studentCache"}, key = "#studentId")
    public String deleteStudent(Long studentId) {
        repository.deleteById(studentId);
        return "record deleted successfully";
    }

    // this method will pause main thread for 5 seconds
    public void simulateBackendCall() {
        try {
            System.out.println("------------- Going to sleep for 5 seconds to simulate Backend Delay -----------");
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}