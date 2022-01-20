package com.university.student;

import com.university.generics.GenericService;
import com.university.generics.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService extends GenericService<Student> {

    private final StudentRepository repository;

    @Autowired
    public StudentService(GenericRepository<Student> genericRepository, StudentRepository repository) {
        super(genericRepository);
        this.repository = repository;
    }

    public List<Student> getByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public List<Student> getByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public List<Student> getByFullName(String firstName, String lastName) {
        return repository.findByFullName(firstName, lastName);
    }

    public List<Student> getByTeacherId(Long id) {
        List<Student> teacher = repository.findByTeacherId(id);
        return teacher;
    }

}
