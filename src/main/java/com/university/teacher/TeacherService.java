package com.university.teacher;

import com.university.generics.GenericService;
import com.university.generics.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService extends GenericService<Teacher> {

    private final TeacherRepository repository;

    @Autowired
    public TeacherService(GenericRepository<Teacher> genericRepository, TeacherRepository repository) {
        super(genericRepository);
        this.repository = repository;
    }

    public List<Teacher> getByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public List<Teacher> getByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public List<Teacher> getByFullName(String firstName, String lastName) {
        return repository.findByFullName(firstName, lastName);
    }

    public List<Teacher> getByStudentId(Long id) {
        return repository.findByStudentId(id);
    }

}
