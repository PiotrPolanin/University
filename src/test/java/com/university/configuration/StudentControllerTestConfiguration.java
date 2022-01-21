package com.university.configuration;

import com.university.generics.GenericRepository;
import com.university.generics.GenericService;
import com.university.student.Student;
import com.university.student.StudentController;
import com.university.student.StudentRepository;
import com.university.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.university")
public class StudentControllerTestConfiguration {

    @Autowired
    private GenericRepository<Student> studentGenericRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GenericService<Student> studentGenericService;
    @Autowired
    private StudentController studentController;

    @Bean
    public StudentService studentService() {
        return new StudentService(studentGenericRepository, studentRepository);
    }

    @Bean
    public StudentController studentController() {
        return new StudentController(studentGenericService, studentService());
    }

}


